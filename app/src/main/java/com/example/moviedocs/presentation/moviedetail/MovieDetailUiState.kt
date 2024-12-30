package com.example.moviedocs.presentation.moviedetail

import com.example.moviedocs.domain.model.externalId.ExternalIdsModel
import com.example.moviedocs.domain.model.country.CountryItemModel
import com.example.moviedocs.domain.model.credits.CastItemModel
import com.example.moviedocs.domain.model.credits.CrewItemModel
import com.example.moviedocs.domain.model.language.LanguageItemModel
import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel
import com.example.moviedocs.domain.model.moviedetail.image.MediaItemModel

sealed interface MovieDetailUiState {
  data object Loading : MovieDetailUiState
  data class Error(val throwable: Throwable) : MovieDetailUiState
  data class Success(
    val movieDetail: MovieDetailModel,
    val externalIds: ExternalIdsModel,
    val languageList: List<LanguageItemModel> = emptyList(),
    val countryList: List<CountryItemModel> = emptyList(),
    val castList: List<CastItemModel> = emptyList(),
    val crewList: List<CrewItemModel> = emptyList(),
    val backDropList: List<MediaItemModel> = emptyList(),
    val logoList: List<MediaItemModel> = emptyList(),
    val posterList: List<MediaItemModel> = emptyList(),
  ) : MovieDetailUiState
}