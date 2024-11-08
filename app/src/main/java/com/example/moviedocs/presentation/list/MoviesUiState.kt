package com.example.moviedocs.presentation.list

import com.example.moviedocs.domain.model.MovieModel

sealed interface MoviesUiState {
  data object FirstPageLoading : MoviesUiState
  data object FirstPageError : MoviesUiState
  data class Success(
    val items: List<MovieModel>,
    val currentPage: Int,
    val nextPageState: MoviesNextPageState
  ) : MoviesUiState
  
  enum class MoviesNextPageState {
    LOADING,  // load more a next page state is loading
    ERROR, // load more a next page state is error
    IDLE, // load more a next page state is idle -> it scrolled a last element -> load more
    DONE // load more a next page state is done -> no more items to load more
  }
}

