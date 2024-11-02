package com.example.moviedocs.presentation.list

import com.example.moviedocs.domain.model.MovieModel

sealed interface MovieListUiState {
  data object Loading : MovieListUiState
  data class Error(val throwable: Throwable) : MovieListUiState
  data class Success(val data: List<MovieModel>) : MovieListUiState
}