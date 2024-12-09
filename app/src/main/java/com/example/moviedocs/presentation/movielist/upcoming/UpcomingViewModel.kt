package com.example.moviedocs.presentation.movielist.upcoming

import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.movielist.MovieListModel
import com.example.moviedocs.domain.usecase.movielist.GetUpcomingUseCase
import com.example.moviedocs.presentation.movielist.BaseMovieListViewModel
import com.example.moviedocs.presentation.movielist.MovieListSingleEvent
import com.example.moviedocs.presentation.movielist.MovieListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject constructor(
  private val getUpcomingUseCase: GetUpcomingUseCase
) : BaseMovieListViewModel() {

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
}