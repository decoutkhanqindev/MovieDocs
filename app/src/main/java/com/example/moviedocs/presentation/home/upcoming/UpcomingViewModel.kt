package com.example.moviedocs.presentation.home.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.domain.usecase.list.GetUpcomingUseCase
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
class UpcomingViewModel
@Inject constructor(
  private val getUpcomingMoviesUseCase: GetUpcomingUseCase
) : ViewModel() {
  
  private val _movieListUiState: MutableStateFlow<MovieListUiState> =
    MutableStateFlow(MovieListUiState.FirstPageLoading)
  val movieListUiState: StateFlow<MovieListUiState> get() = _movieListUiState.asStateFlow()
  
  private val _movieListSingleEvent: Channel<MovieListSingleEvent> =
    Channel(capacity = Channel.UNLIMITED)
  val movieListSingleEvent: Flow<MovieListSingleEvent> get() = _movieListSingleEvent.receiveAsFlow()
  
  init {
    loadFirstPage()
  }
  
  private fun loadFirstPage() {
    viewModelScope.launch {
      _movieListUiState.value = MovieListUiState.FirstPageLoading
      val fistPageResponse: Result<List<MovieModel>> = getUpcomingMoviesUseCase(page = 1)
      fistPageResponse
        .onSuccess { it: List<MovieModel> ->
          _movieListUiState.value = MovieListUiState.Success(
            items = it,
            currentPage = 1,
            nextPageState = if (it.size < MAX_ITEMS_SIZE) {
              MovieListUiState.NextPageState.DONE
            } else {
              MovieListUiState.NextPageState.LOAD_MORE
            }
          )
          _movieListSingleEvent.send(MovieListSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _movieListUiState.value = MovieListUiState.FirstPageError
          _movieListSingleEvent.send(MovieListSingleEvent.Error(it))
          Timber.tag("UpComingViewModel").e("loadFirstPage: ${it.message}")
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
          MovieListUiState.NextPageState.LOADING,
          MovieListUiState.NextPageState.DONE
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
      val nextPage: Int = currentState.currentPage + 1
      val nextPageResponse: Result<List<MovieModel>> = getUpcomingMoviesUseCase(page = nextPage)
      val updatedItems: MutableList<MovieModel> = currentState.items.toMutableList()
      
      nextPageResponse
        .onSuccess { it: List<MovieModel> ->
          updatedItems.addAll(it)
          _movieListUiState.value = MovieListUiState.Success(
            items = updatedItems,
            currentPage = nextPage,
            nextPageState = if (it.size < MAX_ITEMS_SIZE) {
              MovieListUiState.NextPageState.DONE
            } else {
              MovieListUiState.NextPageState.LOAD_MORE
            }
          )
          _movieListSingleEvent.send(MovieListSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _movieListUiState.value = currentState.copy(
            nextPageState = MovieListUiState.NextPageState.ERROR
          )
          _movieListSingleEvent.send(MovieListSingleEvent.Error(it))
          Timber.tag("UpComingViewModel").e("loadNextPageInternal: ${it.message}")
        }
    }
  }
  
  companion object {
    private const val MAX_ITEMS_SIZE = 20
  }
}