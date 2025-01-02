package com.example.moviedocs.data.remote.response.credits

import com.squareup.moshi.Json

data class CreditItemResponse(
  @Json(name = "adult")
  val adult: Boolean?,
  @Json(name = "cast_id")
  val castId: Int?,
  @Json(name = "character")
  val character: String?,
  @Json(name = "department")
  val department: String?,
  @Json(name = "credit_id")
  val creditId: String?,
  @Json(name = "gender")
  val gender: Int,
  @Json(name = "job")
  val job: String?,
  @Json(name = "id")
  val id: Int,
  @Json(name = "known_for_department")
  val knownForDepartment: String?,
  @Json(name = "name")
  val name: String?,
  @Json(name = "order")
  val order: Int?,
  @Json(name = "original_name")
  val originalName: String?,
  @Json(name = "popularity")
  val popularity: Double?,
  @Json(name = "profile_path")
  val profilePath: String?
)