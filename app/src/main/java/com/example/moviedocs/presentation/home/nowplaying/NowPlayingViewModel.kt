package com.example.moviedocs.presentation.home.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.MovieModel
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
@Inject constructor(
  private val getNowPlayingMoviesUseCase: GetNowPlayingUseCase,
) : ViewModel() {
  
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
      val firstPageResponse: Result<List<MovieModel>> = getNowPlayingMoviesUseCase(page = 1)
      firstPageResponse
        .onSuccess { it: List<MovieModel> ->
          _movieListUiState.value = MovieListUiState.Success(
            items = it,
            currentPage = 1,
            nextPageState = if (it.size < MAX_ITEMS_SIZE) {
              MovieListUiState.MovieListNextPageState.DONE // no more pages available
            } else {
              MovieListUiState.MovieListNextPageState.LOAD_MORE // ready to load next page if necessary
            }
          )
          _movieListSingleEvent.send(MovieListSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _movieListUiState.value = MovieListUiState.FirstPageError
          _movieListSingleEvent.send(MovieListSingleEvent.Error(it))
          Timber.tag("NowPlayingMovieListViewModel").e("loadFirstPage: ${it.message}")
        }
    }
  }
  
  fun loadNextPage() {
    when (val currentState: MovieListUiState = _movieListUiState.value) {
      MovieListUiState.FirstPageLoading,
      MovieListUiState.FirstPageError
        -> return
      
      is MovieListUiState.Success -> {
        when (currentState.nextPageState) {
          MovieListUiState.MovieListNextPageState.DONE -> return // no items to load
          MovieListUiState.MovieListNextPageState.LOADING -> return // has in progress request -> avoid duplicate request
          MovieListUiState.MovieListNextPageState.ERROR -> loadFirstPage() // if there was an error, retry the first page
          MovieListUiState.MovieListNextPageState.LOAD_MORE -> loadNextPageInternal(currentState) // load the next page if load more
        }
      }
    }
  }
  
  private fun loadNextPageInternal(currentState: MovieListUiState.Success) {
    viewModelScope.launch {
      _movieListUiState.value = currentState.copy(
        nextPageState = MovieListUiState.MovieListNextPageState.LOADING
      )
      val nextPage: Int = currentState.currentPage + 1
      val nextPageResponse: Result<List<MovieModel>> = getNowPlayingMoviesUseCase(page = nextPage)
      val updateItems: MutableList<MovieModel> = currentState.items.toMutableList()
      
      nextPageResponse.onSuccess { it: List<MovieModel> ->
        // append the newly loaded movies to the list
        updateItems.addAll(it)
        _movieListUiState.value = currentState.copy(
          items = updateItems,
          currentPage = nextPage,
          nextPageState = if (it.size < MAX_ITEMS_SIZE) {
            MovieListUiState.MovieListNextPageState.DONE // no more pages available
          } else {
            MovieListUiState.MovieListNextPageState.LOAD_MORE // ready to load next page if necessary
          }
        )
        _movieListSingleEvent.send(MovieListSingleEvent.Success)
      }
        .onFailure { it: Throwable ->
          _movieListUiState.value = currentState.copy(
            nextPageState = MovieListUiState.MovieListNextPageState.ERROR
          )
          _movieListSingleEvent.send(MovieListSingleEvent.Error(it))
          Timber.tag("NowPlayingMovieListViewModel").e("loadNextPage: ${it.message}")
        }
    }
  }
  
  companion object {
    private const val MAX_ITEMS_SIZE = 20
  }
}