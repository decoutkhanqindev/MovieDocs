package com.example.moviedocs.presentation.movielist

import com.example.moviedocs.domain.model.movielist.MovieItemModel

sealed interface MovieListUiState {
  data object Loading : MovieListUiState
  data class Error(val throwable: Throwable) : MovieListUiState
  
  data class Success(
    val items: List<MovieItemModel>,
    val currentPage: Int,
    val totalPage: Int,
    val totalResults: Int,
  ) : MovieListUiState
  
  enum class SortType {
    TITLE_ASC, TITLE_DSC, RATING_ASC, RATING_DSC
  }
}

