package com.example.moviedocs.presentation.home.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.list.MovieListModel
import com.example.moviedocs.domain.usecase.list.GetNowPlayingUseCase
import com.example.moviedocs.presentation.home.state.MovieListSingleEvent
import com.example.moviedocs.presentation.home.state.MovieListUiState
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
    MutableStateFlow(MovieListUiState.Loading)
  val movieListUiState: StateFlow<MovieListUiState> get() = _movieListUiState.asStateFlow()
  
  // Channel for One-Time Events:
  // This is used to send one-time events like error or success notifications to the UI.
  private val _movieListSingleEvent: Channel<MovieListSingleEvent> =
    Channel(capacity = Channel.UNLIMITED)
  val movieListSingleEvent: Flow<MovieListSingleEvent> get() = _movieListSingleEvent.receiveAsFlow()
  
  init {
    loadPage(1)
  }
  
  fun loadPage(page: Int) {
    viewModelScope.launch {
      _movieListUiState.value = MovieListUiState.Loading
      
      getNowPlayingUseCase(page).onSuccess { it: MovieListModel ->
          _movieListUiState.value = MovieListUiState.Success(
            items = it.results,
            currentPage = it.page,
            totalPage = it.totalPages,
            nextPageState = if (it.page >= it.totalPages) {
              MovieListUiState.NextPageState.DONE
            } else {
              MovieListUiState.NextPageState.LOAD_MORE
            }
          )
          _movieListSingleEvent.send(MovieListSingleEvent.Success)
        }.onFailure { it: Throwable ->
          _movieListUiState.value = MovieListUiState.Error(it)
          _movieListSingleEvent.send(MovieListSingleEvent.Error(it))
          Timber.tag(TAG).e("loadPage: ${it.message}")
        }
    }
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
      _movieListUiState.value = currentState.copy(items = sortedItems)
    }
  }
  
  private companion object {
    private const val TAG = "NowPlayingViewModel"
  }
}