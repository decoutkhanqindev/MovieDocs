package com.example.moviedocs.presentation.home.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.list.MovieItemModel
import com.example.moviedocs.domain.model.list.MovieListModel
import com.example.moviedocs.domain.usecase.list.GetPopularUseCase
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
class PopularViewModel
@Inject constructor(private val getPopularUseCase: GetPopularUseCase) : ViewModel() {
  
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
  
  init {
    loadFirstPage()
  }
  
  private fun loadFirstPage() {
    viewModelScope.launch {
      _movieListUiState.value = MovieListUiState.FirstPageLoading
      val firstPageResponse: Result<MovieListModel> = getPopularUseCase(page = 1)
      
      firstPageResponse
        .onSuccess { it: MovieListModel ->
          _movieListUiState.value = MovieListUiState.Success(
            items = it.results,
            currentPage = it.page,
            nextPageState = if (it.page >= it.totalPages) {
              MovieListUiState.NextPageState.DONE // no more pages available
            } else {
              MovieListUiState.NextPageState.LOAD_MORE // ready to load next page if necessary
            }
          )
          _movieListSingleEvent.send(MovieListSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _movieListUiState.value = MovieListUiState.FirstPageError
          _movieListSingleEvent.send(MovieListSingleEvent.Error(it))
          Timber.tag(TAG).e("loadFirstPage: ${it.message}")
        }
    }
  }
  
  fun loadNextPage() {
    when (val currentState: MovieListUiState = _movieListUiState.value) {
      MovieListUiState.FirstPageLoading,
      MovieListUiState.FirstPageError,
        -> return
      
      is MovieListUiState.Success -> {
        when (currentState.nextPageState) {
          MovieListUiState.NextPageState.DONE -> return // no items to load
          MovieListUiState.NextPageState.LOADING -> return // has in progress request -> avoid duplicate request
          MovieListUiState.NextPageState.ERROR -> loadFirstPage() // if there was an error, retry the first page
          MovieListUiState.NextPageState.LOAD_MORE -> loadNextPageInternal(currentState) // load the next page if load more
        }
      }
    }
  }
  
  private fun loadNextPageInternal(currentState: MovieListUiState.Success) {
    viewModelScope.launch {
      _movieListUiState.value = currentState.copy(
        nextPageState = MovieListUiState.NextPageState.LOADING
      )
      
      val nextPage: Int = currentState.currentPage + 1
      val nextPageResponse: Result<MovieListModel> = getPopularUseCase(page = nextPage)
      val currentItems: MutableList<MovieItemModel> = currentState.items.toMutableList()
      
      nextPageResponse.onSuccess { it: MovieListModel ->
        // append the newly loaded movies to the list
        currentItems.addAll(it.results)
        _movieListUiState.value = currentState.copy(
          items = currentItems,
          currentPage = nextPage,
          nextPageState = if (it.page >= it.totalPages) {
            MovieListUiState.NextPageState.DONE // no more pages available
          } else {
            MovieListUiState.NextPageState.LOAD_MORE // ready to load next page if necessary
          }
        )
        _movieListSingleEvent.send(MovieListSingleEvent.Success)
      }
        .onFailure { it: Throwable ->
          _movieListUiState.value = currentState.copy(
            nextPageState = MovieListUiState.NextPageState.ERROR
          )
          _movieListSingleEvent.send(MovieListSingleEvent.Error(it))
          Timber.tag(TAG).e("loadNextPage: ${it.message}")
        }
    }
  }
  
  private companion object {
    private const val TAG = "PopularViewModel"
  }
}