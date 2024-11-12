package com.example.moviedocs.presentation.home.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.domain.usecase.list.GetPopularUseCase
import com.example.moviedocs.presentation.home.MovieListUiState
import com.example.moviedocs.presentation.home.MovieListSingleEvent
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
  private val getPopularMoviesUseCase: GetPopularUseCase
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
      val firstPageResponse: Result<List<MovieModel>> = getPopularMoviesUseCase(page = 1)
      firstPageResponse
        .onSuccess { it: List<MovieModel> ->
          _movieListUiState.value = MovieListUiState.Success(
            items = it,
            currentPage = 1,
            nextPageState = if (it.size < MAX_ITEMS_SIZE) {
              MovieListUiState.MovieListNextPageState.DONE
            } else {
              MovieListUiState.MovieListNextPageState.LOAD_MORE
            }
          )
          _movieListSingleEvent.send(MovieListSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _movieListUiState.value = MovieListUiState.FirstPageError
          _movieListSingleEvent.send(MovieListSingleEvent.Error(it))
          Timber.tag("getPopularMoviesUseCase").e("loadFirstPage: ${it.message}")
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
          MovieListUiState.MovieListNextPageState.DONE,
          MovieListUiState.MovieListNextPageState.LOADING
            -> return
          
          MovieListUiState.MovieListNextPageState.ERROR -> loadFirstPage()
          MovieListUiState.MovieListNextPageState.LOAD_MORE -> loadNextPageInternal(currentState)
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
      val nextPageResponse: Result<List<MovieModel>> = getPopularMoviesUseCase(page = nextPage)
      val updateItems: MutableList<MovieModel> = currentState.items.toMutableList()
      
      nextPageResponse
        .onSuccess { it: List<MovieModel> ->
          updateItems.addAll(it)
          _movieListUiState.value = MovieListUiState.Success(
            items = updateItems,
            currentPage = nextPage,
            nextPageState = if (it.size < MAX_ITEMS_SIZE) {
              MovieListUiState.MovieListNextPageState.DONE
            } else {
              MovieListUiState.MovieListNextPageState.LOAD_MORE
            }
          )
          _movieListSingleEvent.send(MovieListSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _movieListUiState.value = currentState.copy(
            nextPageState = MovieListUiState.MovieListNextPageState.ERROR
          )
          _movieListSingleEvent.send(MovieListSingleEvent.Error(it))
          Timber.tag("getPopularMoviesUseCase").e("loadNextPageInternal: ${it.message}")
        }
    }
  }
  
  companion object {
    private const val MAX_ITEMS_SIZE = 20
  }
}