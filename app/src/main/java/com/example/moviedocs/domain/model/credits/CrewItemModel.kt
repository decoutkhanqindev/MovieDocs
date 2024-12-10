package com.example.moviedocs.domain.model.credits

data class CrewItemModel(
  val creditId: String,
  val department: String,
  val gender: String,
  val id: Int,
  val job: String,
  val knownForDepartment: String,
  val name: String,
  val originalName: String,
  val popularity: Double,
  val profilePath: String
)