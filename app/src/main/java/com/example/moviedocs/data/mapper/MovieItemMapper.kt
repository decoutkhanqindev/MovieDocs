package com.example.moviedocs.data.mapper

import com.example.moviedocs.data.remote.response.list.MovieItemResponse
import com.example.moviedocs.domain.model.list.MovieItemModel

fun MovieItemResponse.toMovieModel(): MovieItemModel =
  MovieItemModel(
    id = id,
    title = title ?: "Unknown Title",
    overview = overview ?: "No overview available",
    releaseDate = releaseDate ?: "Unknown Date",
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
    posterPath = posterPath ?: "No poster available",
  )