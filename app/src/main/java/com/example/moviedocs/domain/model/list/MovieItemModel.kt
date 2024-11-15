package com.example.moviedocs.domain.model.list

data class MovieItemModel(
  val id: Int,
  val title: String,
  val overview: String,
  val releaseDate: String,
  val voteAverage: Double,
  val voteCount: Int,
  val posterPath: String,
)