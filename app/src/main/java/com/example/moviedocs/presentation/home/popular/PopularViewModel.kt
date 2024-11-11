package com.example.moviedocs.presentation.home.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.domain.usecase.list.GetPopularMoviesUseCase
import com.example.moviedocs.presentation.home.MoviesUiState
import com.example.moviedocs.presentation.home.MoviesSingleEvent
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
@Inject constructor(
  private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {
  
  private val _moviesUiStateFlow: MutableStateFlow<MoviesUiState> =
    MutableStateFlow(MoviesUiState.FirstPageLoading)
  val moviesUiStateFlow: StateFlow<MoviesUiState> get() = _moviesUiStateFlow.asStateFlow()
  
  private val _moviesSingleEvent: Channel<MoviesSingleEvent> = Channel(capacity = Channel.UNLIMITED)
  val moviesSingleEvent: Flow<MoviesSingleEvent> get() = _moviesSingleEvent.receiveAsFlow()
  
  init {
    loadFirstPage()
  }
  
  private fun loadFirstPage() {
    viewModelScope.launch {
      _moviesUiStateFlow.value = MoviesUiState.FirstPageLoading
      val firstPageResponse: Result<List<MovieModel>> = getPopularMoviesUseCase(page = 1)
      firstPageResponse
        .onSuccess { it: List<MovieModel> ->
          _moviesUiStateFlow.value = MoviesUiState.Success(
            items = it,
            currentPage = 1,
            nextPageState = if (it.size < MAX_ITEMS_SIZE) {
              MoviesUiState.MoviesNextPageState.DONE
            } else {
              MoviesUiState.MoviesNextPageState.IDLE
            }
          )
          _moviesSingleEvent.send(MoviesSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _moviesUiStateFlow.value = MoviesUiState.FirstPageError
          _moviesSingleEvent.send(MoviesSingleEvent.Error(it))
          Timber.tag("getPopularMoviesUseCase").e("loadFirstPage: ${it.message}")
        }
    }
  }
  
  fun loadNextPage() {
    when (val currentState: MoviesUiState = _moviesUiStateFlow.value) {
      MoviesUiState.FirstPageLoading, MoviesUiState.FirstPageError -> return
      is MoviesUiState.Success -> {
        when (currentState.nextPageState) {
          MoviesUiState.MoviesNextPageState.DONE,
          MoviesUiState.MoviesNextPageState.LOADING
            -> return
          
          MoviesUiState.MoviesNextPageState.ERROR -> loadFirstPage()
          MoviesUiState.MoviesNextPageState.IDLE -> loadNextPageInternal(currentState)
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
      val nextPageResponse: Result<List<MovieModel>> = getPopularMoviesUseCase(page = nextPage)
      val updateItems: MutableList<MovieModel> = currentState.items.toMutableList()
      
      nextPageResponse
        .onSuccess { it: List<MovieModel> ->
          updateItems.addAll(it)
          _moviesUiStateFlow.value = MoviesUiState.Success(
            items = updateItems,
            currentPage = nextPage,
            nextPageState = if (it.size < MAX_ITEMS_SIZE) {
              MoviesUiState.MoviesNextPageState.DONE
            } else {
              MoviesUiState.MoviesNextPageState.IDLE
            }
          )
          _moviesSingleEvent.send(MoviesSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _moviesUiStateFlow.value = currentState.copy(
            nextPageState = MoviesUiState.MoviesNextPageState.ERROR
          )
          _moviesSingleEvent.send(MoviesSingleEvent.Error(it))
          Timber.tag("getPopularMoviesUseCase").e("loadNextPageInternal: ${it.message}")
        }
    }
  }
  
  companion object {
    private const val MAX_ITEMS_SIZE = 20
  }
}