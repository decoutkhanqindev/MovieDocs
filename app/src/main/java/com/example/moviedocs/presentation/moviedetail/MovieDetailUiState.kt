package com.example.moviedocs.presentation.moviedetail

import com.example.moviedocs.domain.model.country.CountryItemModel
import com.example.moviedocs.domain.model.credits.CreditItemModel
import com.example.moviedocs.domain.model.externalId.ExternalIdsModel
import com.example.moviedocs.domain.model.language.LanguageItemModel
import com.example.moviedocs.domain.model.media.MediaItemModel
import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel
import com.example.moviedocs.domain.model.movielist.MovieItemModel

sealed interface MovieDetailUiState {
  data object Loading : MovieDetailUiState
  data class Error(val throwable: Throwable) : MovieDetailUiState
  data class Success(
    val movieDetail: MovieDetailModel,
    val externalIds: ExternalIdsModel,
    val languageList: List<LanguageItemModel> = emptyList(),
    val countryList: List<CountryItemModel> = emptyList(),
    val castList: List<CreditItemModel> = emptyList(),
    val crewList: List<CreditItemModel> = emptyList(),
    val backDropList: List<MediaItemModel> = emptyList(),
    val logoList: List<MediaItemModel> = emptyList(),
    val posterList: List<MediaItemModel> = emptyList(),
    val similarMovieList: List<MovieItemModel> = emptyList(),
  ) : MovieDetailUiState
}