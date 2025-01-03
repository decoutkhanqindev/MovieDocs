package com.example.moviedocs.presentation.movielist.similar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.movielist.MovieListModel
import com.example.moviedocs.domain.usecase.movielist.GetSimilarMovieListUseCase
import com.example.moviedocs.presentation.movielist.MovieListSingleEvent
import com.example.moviedocs.presentation.movielist.MovieListUiState
import com.example.moviedocs.presentation.movielist.MovieListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SimilarMovieListViewModel @Inject constructor(
  private val getSimilarMovieListUseCase: GetSimilarMovieListUseCase,
  private val savedStateHandle: SavedStateHandle
) : MovieListViewModel() {

  private val movieId: Int = savedStateHandle["movieId"] ?: 0

  init {
    loadPage(movieId, 1)
  }

  fun loadPage(movieId: Int, page: Int) {
    viewModelScope.launch {
      _uiState.value = MovieListUiState.Loading

      getSimilarMovieListUseCase(movieId, page)
        .onSuccess { it: MovieListModel ->
          _uiState.value = MovieListUiState.Success(
            items = it.results,
            currentPage = it.page,
            totalPage = it.totalPages,
            totalResults = it.totalResults
          )
          _singleEvent.send(MovieListSingleEvent.Success)
        }
        .onFailure { it: Throwable ->
          _uiState.value = MovieListUiState.Error(it)
          _singleEvent.send(MovieListSingleEvent.Error(it))
          Timber.tag(this.javaClass.simpleName).e("loadPage: ${it.message}")
        }
    }
  }

  fun setMovieId(movieId: Int) {
    savedStateHandle["movieId"] = movieId
  }
}