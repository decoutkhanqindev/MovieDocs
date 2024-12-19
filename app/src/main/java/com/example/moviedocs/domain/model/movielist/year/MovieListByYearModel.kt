package com.example.moviedocs.domain.model.movielist.year

import com.example.moviedocs.domain.model.movielist.MovieItemModel

data class MovieListByYearModel(
  val year: String,
  val movies: List<MovieItemModel>,
)