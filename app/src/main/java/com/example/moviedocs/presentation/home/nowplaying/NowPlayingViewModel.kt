package com.example.moviedocs.presentation.home.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.list.MovieItemModel
import com.example.moviedocs.domain.model.list.MovieListModel
import com.example.moviedocs.domain.usecase.list.GetNowPlayingUseCase
import com.example.moviedocs.presentation.home.MovieListSingleEvent
import com.example.moviedocs.presentation.home.MovieListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject



@HiltViewModel
class NowPlayingViewModel
@Inject constructor(private val getNowPlayingUseCase: GetNowPlayingUseCase) : ViewModel() {
  
  // StateFlow for State Management:
  // This is used for managing the UI state for the movie list (e.g., loading, success, error).
  private val _movieListUiState: MutableStateFlow<MovieListUiState> =
    MutableStateFlow(MovieListUiState.FirstPageLoading)
  val movieListUiState: StateFlow<MovieListUiState> get() = _movieListUiState.asStateFlow()
  
  // Channel for One-Time Events:
  // This is used to send one-time events like error or success notifications to the UI.
  private val _movieListSingleEvent: Channel<MovieListSingleEvent> =
    Channel(capacity = Channel.UNLIMITED)
  val movieListSingleEvent: Flow<MovieListSingleEvent> get() = _movieListSingleEvent.receiveAsFlow()
  
  private var currentSortType: MovieListUiState.SortType = MovieListUiState.SortType.NONE
  
  init {
    loadFirstPage()
  }
  
  fun sortItems(type: MovieListUiState.SortType) {
    val currentState = _movieListUiState.value
    if (currentState is MovieListUiState.Success) {
      val sortedItems = when (type) {
        MovieListUiState.SortType.TITLE_ASC -> currentState.items.sortedBy { it.title }
        MovieListUiState.SortType.TITLE_DSC -> currentState.items.sortedByDescending { it.title }
        MovieListUiState.SortType.RATING_ASC -> currentState.items.sortedBy { it.voteAverage }
        MovieListUiState.SortType.RATING_DSC -> currentState.items.sortedByDescending { it.voteAverage }
        else -> currentState.items
      }
      currentSortType = type
      _movieListUiState.value = currentState.copy(items = sortedItems)
    }
  }
  
  private fun sortItemsInternal(items: List<MovieItemModel>): List<MovieItemModel> =
    when (currentSortType) {
      MovieListUiState.SortType.TITLE_ASC -> items.sortedBy { it.title }
      MovieListUiState.SortType.TITLE_DSC -> items.sortedByDescending { it.title }
      MovieListUiState.SortType.RATING_ASC -> items.sortedBy { it.voteAverage }
      MovieListUiState.SortType.RATING_DSC -> items.sortedByDescending { it.voteAverage }
      else -> items
    }
  
  private fun loadFirstPage() {
    viewModelScope.launch {
      _movieListUiState.value = MovieListUiState.FirstPageLoading
      val firstPageResponse = getNowPlayingUseCase(page = 1)
      
      firstPageResponse.onSuccess { it: MovieListModel ->
        val sortedItems = sortItemsInternal(it.results)
        _movieListUiState.value = MovieListUiState.Success(
          items = sortedItems,
          currentPage = it.page,
          nextPageState = if (it.page >= it.totalPages) {
            MovieListUiState.NextPageState.DONE
          } else {
            MovieListUiState.NextPageState.LOAD_MORE
          }
        )
        _movieListSingleEvent.send(MovieListSingleEvent.Success)
      }.onFailure { it: Throwable ->
        _movieListUiState.value = MovieListUiState.FirstPageError
        _movieListSingleEvent.send(MovieListSingleEvent.Error(it))
        Timber.tag(TAG).e("loadFirstPage: ${it.message}")
      }
    }
  }
  
  fun loadNextPage() {
    when (val currentState = _movieListUiState.value) {
      MovieListUiState.FirstPageLoading,
      MovieListUiState.FirstPageError,
        -> return
      
      is MovieListUiState.Success -> {
        when (currentState.nextPageState) {
          MovieListUiState.NextPageState.DONE, MovieListUiState.NextPageState.LOADING,
            -> return
          
          MovieListUiState.NextPageState.ERROR -> loadFirstPage()
          MovieListUiState.NextPageState.LOAD_MORE -> loadNextPageInternal(currentState)
        }
      }
    }
  }
  
  private fun loadNextPageInternal(currentState: MovieListUiState.Success) {
    viewModelScope.launch {
      _movieListUiState.value = currentState.copy(
        nextPageState = MovieListUiState.NextPageState.LOADING
      )
      
      val nextPage = currentState.currentPage + 1
      val nextPageResponse = getNowPlayingUseCase(page = nextPage)
      val currentItems = currentState.items.toMutableList()
      
      nextPageResponse.onSuccess { it: MovieListModel ->
        currentItems.addAll(it.results)
        val sortedItems = sortItemsInternal(currentItems)
        _movieListUiState.value = currentState.copy(
          items = sortedItems,
          currentPage = nextPage,
          nextPageState = if (it.page >= it.totalPages) {
            MovieListUiState.NextPageState.DONE
          } else {
            MovieListUiState.NextPageState.LOAD_MORE
          }
        )
        _movieListSingleEvent.send(MovieListSingleEvent.Success)
      }.onFailure { it: Throwable ->
        _movieListUiState.value = currentState.copy(
          nextPageState = MovieListUiState.NextPageState.ERROR
        )
        _movieListSingleEvent.send(MovieListSingleEvent.Error(it))
        Timber.tag(TAG).e("loadNextPage: ${it.message}")
      }
    }
  }
  
  private companion object {
    private const val TAG = "NowPlayingViewModel"
  }
}