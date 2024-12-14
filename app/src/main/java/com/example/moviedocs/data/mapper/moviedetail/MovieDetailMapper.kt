package com.example.moviedocs.data.mapper.moviedetail

import com.example.moviedocs.data.mapper.genre.toGenreItemModel
import com.example.moviedocs.data.mapper.moviedetail.company.toCompanyItemModel
import com.example.moviedocs.data.remote.response.moviedetails.MovieDetailResponse
import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel

fun MovieDetailResponse.toMovieDetailModel(): MovieDetailModel =
  MovieDetailModel(
    id = id,
    posterPath = posterPath?.takeIf { it.isNotEmpty() } ?: "",
    title = title?.takeIf { it.isNotEmpty() } ?: "No title",
    releaseDate = releaseDate?.takeIf { it.isNotEmpty() } ?: "",
    runtime = runtime ?: 0,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
    overview = overview?.takeIf { it.isNotEmpty() } ?: "No overview",
    genres = genres?.map { it.toGenreItemModel() } ?: emptyList(),
    status = status?.takeIf { it.isNotEmpty() } ?: "Unknown status",
    originCountry = originCountry ?: emptyList(),
    originalLanguage = originalLanguage?.takeIf { it.isNotEmpty() } ?: "Unknown original language",
    budget = budget?.takeIf { it != 0L } ?: 0L,
    revenue = revenue?.takeIf { it != 0L } ?: 0L,
    productionCompanies = productionCompanies?.map { it.toCompanyItemModel() } ?: emptyList(),
    homepage = homepage?.takeIf { it.isNotEmpty() } ?: "",
    popularity = popularity?.takeIf { it != 0.0 } ?: 0.0
  )