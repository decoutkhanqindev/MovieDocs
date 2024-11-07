package com.example.moviedocs.presentation.list.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.domain.usecase.list.GetUpcomingMoviesUseCase
import com.example.moviedocs.presentation.list.MoviesNextPageState
import com.example.moviedocs.presentation.list.MoviesSingleEvent
import com.example.moviedocs.presentation.list.MoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel
@Inject constructor(
  private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
) : ViewModel() {
  
  private val _moviesUiStateFlow: MutableStateFlow<MoviesUiState> =
    MutableStateFlow(MoviesUiState.FirstPageLoading)
  val moviesUiStateFlow: StateFlow<MoviesUiState> get() = _moviesUiStateFlow.asStateFlow()
  
  private val _moviesSingleEvent: Channel<MoviesSingleEvent> = Channel(capacity = Channel.UNLIMITED)
  val moviesSingleEvent: Channel<MoviesSingleEvent> get() = _moviesSingleEvent
  
  init {
    loadFirstPage()
  }
  
  private fun loadFirstPage() {
    viewModelScope.launch {
      _moviesUiStateFlow.value = MoviesUiState.FirstPageLoading
      val fistPageResponse: Result<List<MovieModel>> = getUpcomingMoviesUseCase(page = 1)
      fistPageResponse
        .onSuccess { it: List<MovieModel> ->
          _moviesUiStateFlow.value = MoviesUiState.Success(
            items = it,
            currentPage = 1,
            nextPageState = if (it.size < MAX_ITEMS_SIZE) {
              MoviesNextPageState.DONE
            } else {
              MoviesNextPageState.IDLE
            }
          )
          _moviesSingleEvent.send(MoviesSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _moviesUiStateFlow.value = MoviesUiState.FirstPageError
          _moviesSingleEvent.send(MoviesSingleEvent.Error(it))
          Timber.tag("UpComingViewModel").e("loadFirstPage: ${it.message}")
        }
    }
  }
  
  fun loadNextPage() {
    when (val currentState: MoviesUiState = _moviesUiStateFlow.value) {
      MoviesUiState.FirstPageLoading, MoviesUiState.FirstPageError -> return
      is MoviesUiState.Success -> {
        when (currentState.nextPageState) {
          MoviesNextPageState.LOADING, MoviesNextPageState.DONE -> return
          MoviesNextPageState.ERROR -> loadFirstPage()
          MoviesNextPageState.IDLE -> loadNextPageInternal(currentState)
        }
      }
    }
  }
  
  private fun loadNextPageInternal(currentState: MoviesUiState.Success) {
    viewModelScope.launch {
      _moviesUiStateFlow.value = currentState.copy(nextPageState = MoviesNextPageState.LOADING)
      val nextPage: Int = currentState.currentPage + 1
      val nextPageResponse: Result<List<MovieModel>> = getUpcomingMoviesUseCase(page = nextPage)
      val updatedItems: MutableList<MovieModel> = currentState.items.toMutableList()
      
      nextPageResponse
        .onSuccess { it: List<MovieModel> ->
          updatedItems.addAll(it)
          _moviesUiStateFlow.value = MoviesUiState.Success(
            items = updatedItems,
            currentPage = nextPage,
            nextPageState = if (it.size < MAX_ITEMS_SIZE) {
              MoviesNextPageState.DONE
            } else {
              MoviesNextPageState.IDLE
            }
          )
          _moviesSingleEvent.send(MoviesSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _moviesUiStateFlow.value = currentState.copy(nextPageState = MoviesNextPageState.ERROR)
          _moviesSingleEvent.send(MoviesSingleEvent.Error(it))
          Timber.tag("UpComingViewModel").e("loadNextPageInternal: ${it.message}")
        }
    }
  }
  
  companion object {
    private const val MAX_ITEMS_SIZE = 20
  }
}