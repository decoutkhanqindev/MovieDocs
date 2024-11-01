package com.example.moviedocs.domain.model

data class MovieModel(
  val id: Int,
  val title: String,
  val overview: String,
  val releaseDate: String,
  val voteAverage: Double,
  val posterPath: String
)