package com.example.moviedocs.presentation.list

import com.example.moviedocs.domain.model.MovieModel

sealed interface MovieListUiState {
  data object FirstPageLoading : MovieListUiState
  data object FirstPageError : MovieListUiState
  data class Success(
    val items: List<MovieModel>,
    val currentPage: Int,
    val nextPageState: MovieListNextPageState
  ) : MovieListUiState
}

enum class MovieListNextPageState {
  LOADING, ERROR, IDLE, NO_MORE_ITEMS
}
