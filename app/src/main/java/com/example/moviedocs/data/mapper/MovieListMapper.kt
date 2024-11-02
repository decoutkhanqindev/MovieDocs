package com.example.moviedocs.data.mapper

import com.example.moviedocs.data.remote.response.list.MovieListResponse
import com.example.moviedocs.domain.model.MovieModel

fun MovieListResponse.Movie.toMovieListModel(): MovieModel =
  MovieModel(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage
  )