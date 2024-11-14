package com.example.moviedocs.data.mapper

import com.example.moviedocs.data.remote.response.list.MovieItemResponse
import com.example.moviedocs.domain.model.list.MovieItemModel

fun MovieItemResponse.toMovieModel(): MovieItemModel =
  MovieItemModel(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath,
  )