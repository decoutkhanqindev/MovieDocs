package com.example.moviedocs.domain.repository.moviedetail

import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel
import com.example.moviedocs.domain.model.moviedetail.country.CountryItemModel
import com.example.moviedocs.domain.model.moviedetail.external.ExternalIdsModel
import com.example.moviedocs.domain.model.moviedetail.language.LanguageItemModel

interface MovieDetailRepository {
  suspend fun getMovieDetail(movieId: Int): Result<MovieDetailModel>
  suspend fun getExternalIds(movieId: Int): Result<ExternalIdsModel>
  suspend fun getCountryList(): Result<List<CountryItemModel>>
  suspend fun getLanguageList(): Result<List<LanguageItemModel>>
}