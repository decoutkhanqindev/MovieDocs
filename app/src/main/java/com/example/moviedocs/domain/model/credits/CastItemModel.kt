package com.example.moviedocs.domain.model.credits

data class CastItemModel(
  val castId: Int,
  val character: String,
  val creditId: String,
  val gender: String,
  val id: Int,
  val knownForDepartment: String,
  val name: String,
  val originalName: String,
  val popularity: Double,
  val profilePath: String
)