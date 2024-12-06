package com.example.moviedocs.data.mapper.movielist

import com.example.moviedocs.data.remote.response.movielist.MovieItemResponse
import com.example.moviedocs.domain.model.movielist.MovieItemModel

fun MovieItemResponse.toMovieModel(): MovieItemModel =
  MovieItemModel(
    id = id,
    title = title ?: "No title",
    overview = overview ?: "No overview",
    releaseDate = releaseDate ?: "No release date",
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
    posterPath = posterPath ?: "No poster",
  )