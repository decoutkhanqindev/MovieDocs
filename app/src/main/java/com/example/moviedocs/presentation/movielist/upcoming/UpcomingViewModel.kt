package com.example.moviedocs.presentation.movielist.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.movielist.MovieListModel
import com.example.moviedocs.domain.usecase.movielist.GetUpcomingUseCase
import com.example.moviedocs.presentation.movielist.MovieListSingleEvent
import com.example.moviedocs.presentation.movielist.MovieListUiState
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
class UpcomingViewModel @Inject constructor(
  private val getUpcomingUseCase: GetUpcomingUseCase
) : ViewModel() {
  
  // StateFlow for State Management:
  // This is used for managing the UI state for the movie list (e.g., loading, success, error).
  private val _uiState: MutableStateFlow<MovieListUiState> =
    MutableStateFlow(MovieListUiState.Loading)
  val uiState: StateFlow<MovieListUiState> get() = _uiState.asStateFlow()
  
  // Channel for One-Time Events:
  // This is used to send one-time events like error or success notifications to the UI.
  private val _singleEvent: Channel<MovieListSingleEvent> =
    Channel(capacity = Channel.UNLIMITED)
  val singleEvent: Flow<MovieListSingleEvent> get() = _singleEvent.receiveAsFlow()
  
  init {
    loadPage(1)
  }
  
  fun loadPage(page: Int) {
    viewModelScope.launch {
      _uiState.value = MovieListUiState.Loading
      
      getUpcomingUseCase(page).onSuccess { it: MovieListModel ->
        _uiState.value = MovieListUiState.Success(
          items = it.results,
          currentPage = it.page,
          totalPage = it.totalPages,
          totalResults = it.totalResults
        )
        _singleEvent.send(MovieListSingleEvent.Success)
      }.onFailure { it: Throwable ->
        _uiState.value = MovieListUiState.Error(it)
        _singleEvent.send(MovieListSingleEvent.Error(it))
        Timber.tag(this.javaClass.simpleName).e("loadPage: ${it.message}")
      }
    }
  }
  
  fun sortItems(type: MovieListUiState.SortType) {
    val currentState = _uiState.value
    if (currentState is MovieListUiState.Success) {
      val sortedItems = when (type) {
        MovieListUiState.SortType.TITLE_ASC -> currentState.items.sortedBy { it.title }
        MovieListUiState.SortType.TITLE_DSC -> currentState.items.sortedByDescending { it.title }
        MovieListUiState.SortType.RATING_ASC -> currentState.items.sortedBy { it.voteAverage }
        MovieListUiState.SortType.RATING_DSC -> currentState.items.sortedByDescending { it.voteAverage }
        else -> currentState.items
      }
      _uiState.value = currentState.copy(items = sortedItems)
    }
  }
}