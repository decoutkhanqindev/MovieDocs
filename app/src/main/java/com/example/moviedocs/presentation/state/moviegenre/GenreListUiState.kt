package com.example.moviedocs.presentation.state.moviegenre

import com.example.moviedocs.domain.model.moviegenre.GenreItemModel

sealed interface GenreListUiState {
  data object Loading : GenreListUiState
  data class Error(val throwable: Throwable) : GenreListUiState
  data class Success(val items: List<GenreItemModel>) : GenreListUiState
}