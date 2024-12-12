package com.example.moviedocs.data.remote.response.genre

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class GenreListResponse(
  @Json(name = "genres")
  val genres: List<GenreItemResponse>,
)

