package com.example.moviedocs.data.mapper.moviedetail

import com.example.moviedocs.data.mapper.moviedetail.company.toCompanyListModel
import com.example.moviedocs.data.mapper.moviegenre.toGenreListModel
import com.example.moviedocs.data.remote.response.moviedetails.MovieDetailResponse
import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel

fun MovieDetailResponse.toMovieDetailModel(): MovieDetailModel =
  MovieDetailModel(
    id = id,
    posterPath = posterPath,
    originalTitle = originalTitle,
    releaseDate = releaseDate,
    runtime = runtime,
    voteAverage = voteAverage,
    voteCount = voteCount,
    overview = overview,
    genres = genres.toGenreListModel(),
    status = status,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    budget = budget,
    revenue = revenue,
    productionCompanies = productionCompanies.toCompanyListModel(),
  )