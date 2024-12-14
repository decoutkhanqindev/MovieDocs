package com.example.moviedocs.data.mapper.movielist

import com.example.moviedocs.data.remote.response.movielist.MovieItemResponse
import com.example.moviedocs.domain.model.movielist.MovieItemModel

fun MovieItemResponse.toMovieItemModel(): MovieItemModel =
  MovieItemModel(
    id = id,
    title = title?.takeIf { it.isNotEmpty() } ?: "No title",
    overview = overview?.takeIf { it.isNotEmpty() } ?: "No overview",
    releaseDate = releaseDate?.takeIf { it.isNotEmpty() } ?: "",
    voteAverage = voteAverage?.takeIf { it != 0.0 } ?: 0.0,
    voteCount = voteCount?.takeIf { it != 0 } ?: 0,
    posterPath = posterPath?.takeIf { it.isNotEmpty() } ?: "",
    popularity = popularity?.takeIf { it != 0.0 } ?: 0.0,

    // specific to cast
    character = character?.takeIf { it.isNotEmpty() } ?: "Unknown character",

    // specific to crew
    department = department?.takeIf { it.isNotEmpty() } ?: "Unknown department",
    job = job?.takeIf { it.isNotEmpty() } ?: "Unknown job",
  )