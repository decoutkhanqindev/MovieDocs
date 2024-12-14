package com.example.moviedocs.domain.model.movielist

data class MovieItemModel(
  val id: Int,
  val title: String,
  val overview: String,
  val releaseDate: String,
  val voteAverage: Double,
  val voteCount: Int,
  val posterPath: String,
  val popularity: Double,

  // specific to cast
  val character: String,

  // specific to crew
  val department: String,
  val job: String,

//  val creditId: String,
)