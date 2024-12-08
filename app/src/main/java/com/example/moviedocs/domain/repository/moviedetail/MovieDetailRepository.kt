package com.example.moviedocs.domain.repository.moviedetail

import com.example.moviedocs.domain.model.country.CountryItemModel
import com.example.moviedocs.domain.model.language.LanguageItemModel
import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel
import com.example.moviedocs.domain.model.moviedetail.external.ExternalIdsModel

interface MovieDetailRepository {
  suspend fun getMovieDetail(movieId: Int): Result<MovieDetailModel>
  suspend fun getExternalIds(movieId: Int): Result<ExternalIdsModel>
}