package com.example.moviedocs.presentation.moviedetail

import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel
import com.example.moviedocs.domain.model.moviedetail.external.ExternalIdsModel

sealed interface MovieDetailUiState {
  data object Loading : MovieDetailUiState
  data class Error(val throwable: Throwable) : MovieDetailUiState
  data class Success(
    val movieDetail: MovieDetailModel,
    val externalIds: ExternalIdsModel,
  ) : MovieDetailUiState
}