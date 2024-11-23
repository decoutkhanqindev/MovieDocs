package com.example.moviedocs.data.remote.response.moviegenre

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class GenreItemResponse(
  @Json(name = "id")
  val id: Int, // 28
  @Json(name = "name")
  val name: String, // Action
)