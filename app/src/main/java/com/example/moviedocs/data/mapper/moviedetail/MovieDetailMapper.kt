package com.example.moviedocs.data.mapper.moviedetail

import com.example.moviedocs.data.mapper.moviedetail.company.toCompanyItemModel
import com.example.moviedocs.data.mapper.moviegenre.toGenreItemModel
import com.example.moviedocs.data.remote.response.moviedetails.MovieDetailResponse
import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel

fun MovieDetailResponse.toMovieDetailModel(): MovieDetailModel =
  MovieDetailModel(
    id = id,
    posterPath = posterPath,
    title = title,
    releaseDate = releaseDate,
    runtime = runtime,
    voteAverage = voteAverage,
    voteCount = voteCount,
    overview = overview,
    genres = genres.map { it.toGenreItemModel() },
    status = status,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    budget = budget,
    revenue = revenue,
    productionCompanies = productionCompanies.map { it.toCompanyItemModel() },
  )