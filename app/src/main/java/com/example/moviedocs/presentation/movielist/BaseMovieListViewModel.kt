package com.example.moviedocs.presentation.movielist

import androidx.lifecycle.ViewModel
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseMovieListViewModel : ViewModel() {

  // StateFlow for State Management:
  // This is used for managing the UI state for the movie list (e.g., loading, success, error).
  internal val _uiState: MutableStateFlow<MovieListUiState> =
    MutableStateFlow(MovieListUiState.Loading)
  val uiState: StateFlow<MovieListUiState> get() = _uiState.asStateFlow()

  // Channel for One-Time Events:
  // This is used to send one-time events like error or success notifications to the UI.
  internal val _singleEvent: Channel<MovieListSingleEvent> =
    Channel(capacity = Channel.UNLIMITED)
  val singleEvent: Flow<MovieListSingleEvent> get() = _singleEvent.receiveAsFlow()

  protected fun sortList(type: MovieListUiState.SortType) {
    val currentState: MovieListUiState = _uiState.value
    if (currentState is MovieListUiState.Success) {
      val sortedList: List<MovieItemModel> = when (type) {
        MovieListUiState.SortType.TITLE_ASC -> currentState.items.sortedBy { it.title }
        MovieListUiState.SortType.TITLE_DSC -> currentState.items.sortedByDescending { it.title }
        MovieListUiState.SortType.RATING_ASC -> currentState.items.sortedBy { it.voteAverage }
        MovieListUiState.SortType.RATING_DSC -> currentState.items.sortedByDescending { it.voteAverage }
      }
      _uiState.value = currentState.copy(items = sortedList)
    }
  }
}