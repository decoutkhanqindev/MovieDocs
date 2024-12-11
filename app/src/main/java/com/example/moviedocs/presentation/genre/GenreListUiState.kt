package com.example.moviedocs.presentation.genre

import com.example.moviedocs.domain.model.genre.GenreItemModel

sealed interface GenreListUiState {
  data object Loading : GenreListUiState
  data class Error(val throwable: Throwable) : GenreListUiState
  data class Success(val items: List<GenreItemModel>) : GenreListUiState
}