package com.example.moviedocs.presentation.list.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.domain.usecase.list.GetNowPlayingMoviesUseCase
import com.example.moviedocs.presentation.list.MoviesSingleEvent
import com.example.moviedocs.presentation.list.MoviesUiState
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
  private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
) : ViewModel() {
  
  // StateFlow for State Management:
  // This is used for managing the UI state for the movie list (e.g., loading, success, error).
  private val _moviesUiStateFlow: MutableStateFlow<MoviesUiState> =
    MutableStateFlow(MoviesUiState.FirstPageLoading)
  val moviesUiStateFlow: StateFlow<MoviesUiState> get() = _moviesUiStateFlow.asStateFlow()
  
  // Channel for One-Time Events:
  // This is used to send one-time events like error or success notifications to the UI.
  private val _moviesSingleEvent: Channel<MoviesSingleEvent> = Channel(capacity = Channel.UNLIMITED)
  val moviesSingleEvent: Flow<MoviesSingleEvent> get() = _moviesSingleEvent.receiveAsFlow()
  
  init {
    loadFirstPage()
  }
  
  private fun loadFirstPage() {
    viewModelScope.launch {
      _moviesUiStateFlow.value = MoviesUiState.FirstPageLoading
      val firstPageResponse: Result<List<MovieModel>> = getNowPlayingMoviesUseCase(page = 1)
      firstPageResponse
        .onSuccess { it: List<MovieModel> ->
          _moviesUiStateFlow.value = MoviesUiState.Success(
            items = it, currentPage = 1, nextPageState = if (it.size < MAX_ITEMS_SIZE) {
              MoviesUiState.MoviesNextPageState.DONE // no more pages available
            } else {
              MoviesUiState.MoviesNextPageState.IDLE // ready to load next page if necessary
            }
          )
          _moviesSingleEvent.send(MoviesSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _moviesUiStateFlow.value = MoviesUiState.FirstPageError
          _moviesSingleEvent.send(MoviesSingleEvent.Error(it))
          Timber.tag("NowPlayingMovieListViewModel").e("loadFirstPage: ${it.message}")
        }
    }
  }
  
  fun loadNextPage() {
    when (val currentState: MoviesUiState = _moviesUiStateFlow.value) {
      MoviesUiState.FirstPageLoading, MoviesUiState.FirstPageError -> return
      is MoviesUiState.Success -> {
        when (currentState.nextPageState) {
          MoviesUiState.MoviesNextPageState.DONE -> return // no items to load
          MoviesUiState.MoviesNextPageState.LOADING -> return // has in progress request -> avoid duplicate request
          MoviesUiState.MoviesNextPageState.ERROR -> loadFirstPage() // if there was an error, retry the first page
          MoviesUiState.MoviesNextPageState.IDLE -> loadNextPageInternal(currentState) // load the next page if idle
        }
      }
    }
  }
  
  private fun loadNextPageInternal(currentState: MoviesUiState.Success) {
    viewModelScope.launch {
      _moviesUiStateFlow.value = currentState.copy(
        nextPageState = MoviesUiState.MoviesNextPageState.LOADING
      )
      val nextPage: Int = currentState.currentPage + 1
      val nextPageResponse: Result<List<MovieModel>> = getNowPlayingMoviesUseCase(page = nextPage)
      val updateItems: MutableList<MovieModel> = currentState.items.toMutableList()
      
      nextPageResponse.onSuccess { it: List<MovieModel> ->
        // append the newly loaded movies to the list
        updateItems.addAll(it)
        _moviesUiStateFlow.value = currentState.copy(
          items = updateItems,
          currentPage = nextPage,
          nextPageState = if (it.size < MAX_ITEMS_SIZE) {
            MoviesUiState.MoviesNextPageState.DONE // no more pages available
          } else {
            MoviesUiState.MoviesNextPageState.IDLE // ready to load next page if necessary
          }
        )
        _moviesSingleEvent.send(MoviesSingleEvent.Success)
      }
        .onFailure { it: Throwable ->
          _moviesUiStateFlow.value = currentState.copy(
            nextPageState = MoviesUiState.MoviesNextPageState.ERROR
          )
          _moviesSingleEvent.send(MoviesSingleEvent.Error(it))
          Timber.tag("NowPlayingMovieListViewModel").e("loadNextPage: ${it.message}")
        }
    }
  }
  
  companion object {
    private const val MAX_ITEMS_SIZE = 20
  }
}