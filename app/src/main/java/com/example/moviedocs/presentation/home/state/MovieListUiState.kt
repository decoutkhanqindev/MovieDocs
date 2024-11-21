package com.example.moviedocs.presentation.home.state

import com.example.moviedocs.domain.model.list.MovieItemModel

sealed interface MovieListUiState {
  data object Loading : MovieListUiState
  data class Error(val throwable: Throwable) : MovieListUiState
  
  data class Success(
    val items: List<MovieItemModel>,
    val currentPage: Int,
    val totalPage: Int,
    val nextPageState: NextPageState,
  ) : MovieListUiState
  
  enum class NextPageState {
    LOADING,  // load more a next page state is loading
    ERROR, // load more a next page state is error
    LOAD_MORE, // load more a next page state is idle -> it scrolled a last element -> load more
    DONE // load more a next page state is done -> no more items to load more
  }
  
  enum class SortType {
    NONE, TITLE_ASC, TITLE_DSC, RATING_ASC, RATING_DSC
  }
}
