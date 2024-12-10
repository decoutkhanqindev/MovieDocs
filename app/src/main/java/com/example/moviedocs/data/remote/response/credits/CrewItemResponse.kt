package com.example.moviedocs.data.remote.response.credits

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CrewItemResponse(
  @Json(name = "adult")
  val adult: Boolean?,
  @Json(name = "credit_id")
  val creditId: String,
  @Json(name = "department")
  val department: String?,
  @Json(name = "gender")
  val gender: Int,
  @Json(name = "id")
  val id: Int,
  @Json(name = "job")
  val job: String?,
  @Json(name = "known_for_department")
  val knownForDepartment: String?,
  @Json(name = "name")
  val name: String?,
  @Json(name = "original_name")
  val originalName: String?,
  @Json(name = "popularity")
  val popularity: Double?,
  @Json(name = "profile_path")
  val profilePath: String?
)