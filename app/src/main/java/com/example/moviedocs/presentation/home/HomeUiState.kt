package com.example.moviedocs.presentation.home

import com.example.moviedocs.domain.model.genre.GenreItemModel
import com.example.moviedocs.domain.model.movielist.MovieItemModel

sealed interface HomeUiState {
  data object Loading : HomeUiState
  data class Error(val throwable: Throwable) : HomeUiState
  data class Success(
    val genreList: List<GenreItemModel> = emptyList(),
    val nowPlaying: List<MovieItemModel> = emptyList(),
    val popular: List<MovieItemModel> = emptyList(),
    val upcoming: List<MovieItemModel> = emptyList(),
    val topRated: List<MovieItemModel> = emptyList(),
  ) : HomeUiState
}