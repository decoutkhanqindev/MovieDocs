package com.example.moviedocs.data.remote.response.credits

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CreditListResponse(
  @Json(name = "cast")
  val cast: List<CastItemResponse>,
  @Json(name = "crew")
  val crew: List<CrewItemResponse>,
  @Json(name = "id")
  val id: Int // movie id
)