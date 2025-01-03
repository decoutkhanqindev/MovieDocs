package com.example.moviedocs.domain.model.movielist

data class MovieListModel(
  val page: Int,
  val results: List<MovieItemModel>,
  val totalPages: Int,
  val totalResults: Int,
)