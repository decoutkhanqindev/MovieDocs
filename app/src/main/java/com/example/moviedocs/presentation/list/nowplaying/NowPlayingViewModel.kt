package com.example.moviedocs.presentation.list.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.domain.usecase.list.GetNowPlayingMoviesUseCase
import com.example.moviedocs.presentation.list.MovieListNextPageState
import com.example.moviedocs.presentation.list.MoviesSingleEvent
import com.example.moviedocs.presentation.list.MovieListUiState
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
  private val _movieListUiStateFlow: MutableStateFlow<MovieListUiState> =
    MutableStateFlow(MovieListUiState.FirstPageLoading)
  val movieListUiStateFlow: StateFlow<MovieListUiState> get() = _movieListUiStateFlow.asStateFlow()
  
  // Channel for One-Time Events:
  private val _movieListSingleEvent: Channel<MoviesSingleEvent> = Channel(Channel.UNLIMITED)
  val movieListSingleEvent: Flow<MoviesSingleEvent> get() = _movieListSingleEvent.receiveAsFlow()
  
  init {
    loadFirstPage()
  }
  
  private fun loadFirstPage() {
    viewModelScope.launch {
      _movieListUiStateFlow.value = MovieListUiState.FirstPageLoading
      val firstPageMovieModel: Result<List<MovieModel>> = getNowPlayingMoviesUseCase(page = 1)
      firstPageMovieModel
        .onSuccess { it: List<MovieModel> ->
          _movieListUiStateFlow.value = MovieListUiState.Success(
            items = it,
            currentPage = 1,
            nextPageState = if (it.size < MAX_ITEMS_SIZE) {
              MovieListNextPageState.NO_MORE_ITEMS
            } else {
              MovieListNextPageState.IDLE
            }
          )
          _movieListSingleEvent.send(MoviesSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _movieListUiStateFlow.value = MovieListUiState.FirstPageError
          _movieListSingleEvent.send(MoviesSingleEvent.Error(it))
          Timber.tag("NowPlayingMovieListViewModel").e("loadFirstPage: ${it.message}")
        }
    }
  }
  
  fun loadNextPage() {
    when (val currentState: MovieListUiState = _movieListUiStateFlow.value) {
      MovieListUiState.FirstPageLoading, MovieListUiState.FirstPageError -> return
      is MovieListUiState.Success -> {
        when (currentState.nextPageState) {
          MovieListNextPageState.NO_MORE_ITEMS -> return
          MovieListNextPageState.LOADING -> return
          MovieListNextPageState.ERROR -> loadFirstPage()
          MovieListNextPageState.IDLE -> {
            loadNextPageInternal(currentState)
          }
        }
      }
    }
  }
  
  private fun loadNextPageInternal(state: MovieListUiState.Success) {
    viewModelScope.launch {
      _movieListUiStateFlow.value = state.copy(nextPageState = MovieListNextPageState.LOADING)
      val nextPage: Int = state.currentPage + 1
      val nextPageMovieModel: Result<List<MovieModel>> =
        getNowPlayingMoviesUseCase(page = nextPage)
      nextPageMovieModel.onSuccess { it: List<MovieModel> ->
        _movieListUiStateFlow.value = state.copy(
          items = state.items + it,
          currentPage = nextPage,
          nextPageState = if (it.size < MAX_ITEMS_SIZE) {
            MovieListNextPageState.NO_MORE_ITEMS
          } else {
            MovieListNextPageState.IDLE
          }
        )
        _movieListSingleEvent.send(MoviesSingleEvent.Success)
      }.onFailure { it: Throwable ->
        _movieListUiStateFlow.value = state.copy(nextPageState = MovieListNextPageState.ERROR)
        _movieListSingleEvent.send(MoviesSingleEvent.Error(it))
      }
    }
  }
  
  companion object {
    private const val MAX_ITEMS_SIZE = 20
  }
}