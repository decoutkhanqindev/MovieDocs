package com.example.moviedocs.domain.model.person

data class PersonDetailModel(
  val alsoKnownAs: List<String>,
  val biography: String,
  val birthday: String,
  val deathday: String,
  val gender: String,
  val homepage: String,
  val id: Int,
  val knownForDepartment: String,
  val name: String,
  val placeOfBirth: String,
  val popularity: Double,
  val profilePath: String
)
