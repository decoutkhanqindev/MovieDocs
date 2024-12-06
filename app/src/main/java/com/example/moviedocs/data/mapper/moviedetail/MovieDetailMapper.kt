package com.example.moviedocs.data.mapper.moviedetail

import com.example.moviedocs.data.mapper.moviedetail.company.toCompanyItemModel
import com.example.moviedocs.data.mapper.moviegenre.toGenreItemModel
import com.example.moviedocs.data.remote.response.moviedetails.MovieDetailResponse
import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel

fun MovieDetailResponse.toMovieDetailModel(): MovieDetailModel =
  MovieDetailModel(
    id = id,
    posterPath = posterPath ?: "No poster found",
    title = title ?: "No title found",
    releaseDate = releaseDate ?: "No release date found",
    runtime = runtime ?: 0,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
    overview = if(overview.equals("")) "No overview" else overview.toString(),
    genres = genres?.map { it.toGenreItemModel() } ?: emptyList(),
    status = status ?: "No status found",
    originCountry = originCountry ?: emptyList(),
    originalLanguage = originalLanguage ?: "No original language found",
    budget = budget ?: 0,
    revenue = revenue ?: 0,
    productionCompanies = productionCompanies?.map { it.toCompanyItemModel() } ?: emptyList(),
    homepage = homepage ?: "No homepage found",
  )