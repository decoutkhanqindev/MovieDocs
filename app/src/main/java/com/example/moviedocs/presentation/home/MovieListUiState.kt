package com.example.moviedocs.presentation.home

import com.example.moviedocs.domain.model.list.MovieItemModel

sealed interface MovieListUiState {
  data object FirstPageLoading : MovieListUiState
  data object FirstPageError : MovieListUiState
  
  data class Success(
    val items: List<MovieItemModel>,
    val currentPage: Int,
    val nextPageState: NextPageState,
  ) : MovieListUiState
  
  enum class NextPageState {
    LOADING,  // load more a next page state is loading
    ERROR, // load more a next page state is error
    LOAD_MORE, // load more a next page state is idle -> it scrolled a last element -> load more
    DONE // load more a next page state is done -> no more items to load more
  }
}

