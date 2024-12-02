package com.example.moviedocs.presentation.moviedetail

import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel
import com.example.moviedocs.domain.model.moviedetail.country.CountryItemModel
import com.example.moviedocs.domain.model.moviedetail.external.ExternalIdsModel
import com.example.moviedocs.domain.model.moviedetail.language.LanguageItemModel

sealed interface MovieDetailUiState {
  data object Loading : MovieDetailUiState
  data class Error(val throwable: Throwable) : MovieDetailUiState
  data class Success(
    val movieDetail: MovieDetailModel,
    val externalIds: ExternalIdsModel,
    val languageList: List<LanguageItemModel> = emptyList(),
    val countryList: List<CountryItemModel> = emptyList(),
  ) : MovieDetailUiState
}