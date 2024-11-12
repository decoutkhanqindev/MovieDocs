package com.example.moviedocs.data.mapper

import com.example.moviedocs.data.remote.response.list.MovieResponse
import com.example.moviedocs.domain.model.MovieModel

fun MovieResponse.toMovieModel(): MovieModel =
  MovieModel(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath
  )