package com.example.moviedocs.presentation.movielist

import androidx.lifecycle.ViewModel
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class MovieListViewModel : ViewModel() {

  // StateFlow for State Management:
  // This is used for managing the UI state for the movie list (e.g., loading, success, error).
  protected val _uiState: MutableStateFlow<MovieListUiState> =
    MutableStateFlow(MovieListUiState.Loading)
  val uiState: StateFlow<MovieListUiState> get() = _uiState.asStateFlow()

  // Channel for One-Time Events:
  // This is used to send one-time events like error or success notifications to the UI.
  protected val _singleEvent: Channel<MovieListSingleEvent> =
    Channel(capacity = Channel.UNLIMITED)
  val singleEvent: Flow<MovieListSingleEvent> get() = _singleEvent.receiveAsFlow()

  internal fun sortList(type: MovieListUiState.SortType) {
    val currentState: MovieListUiState = _uiState.value
    if (currentState is MovieListUiState.Success) {
      val currentList: List<MovieItemModel> = currentState.items
      val sortedList: List<MovieItemModel> = when (type) {
        MovieListUiState.SortType.TITLE_ASC -> currentList.sortedBy { it.title }
        MovieListUiState.SortType.TITLE_DSC -> currentList.sortedByDescending { it.title }
        MovieListUiState.SortType.RATING_ASC -> currentList.sortedBy { it.voteAverage }
        MovieListUiState.SortType.RATING_DSC -> currentList.sortedByDescending { it.voteAverage }
        MovieListUiState.SortType.POPULARITY_ASC -> currentList.sortedBy { it.popularity }
        MovieListUiState.SortType.POPULARITY_DSC -> currentList.sortedByDescending { it.popularity }
      }
      _uiState.value = currentState.copy(items = sortedList)
    }
  }
}