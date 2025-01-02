package com.example.moviedocs.domain.model.credits

data class CreditItemModel(
  val castId: Int,
  val character: String,
  val creditId: String,
  val department: String,
  val gender: String,
  val job: String,
  val id: Int,
  val knownForDepartment: String,
  val name: String,
  val originalName: String,
  val popularity: Double,
  val profilePath: String
)