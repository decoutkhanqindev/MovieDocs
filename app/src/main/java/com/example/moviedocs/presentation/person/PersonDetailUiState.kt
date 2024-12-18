package com.example.moviedocs.presentation.person

import com.example.moviedocs.domain.externalId.ExternalIdsModel
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.domain.person.PersonDetailModel

sealed interface PersonDetailUiState {
  data object Loading : PersonDetailUiState
  data class Error(val throwable: Throwable) : PersonDetailUiState
  data class Success(
    val personDetail: PersonDetailModel,
    val personExternalIds: ExternalIdsModel,
    val castMovieCreditList: List<MovieItemModel> = emptyList(),
    val crewMovieCreditList: List<MovieItemModel> = emptyList(),
  ) : PersonDetailUiState
}