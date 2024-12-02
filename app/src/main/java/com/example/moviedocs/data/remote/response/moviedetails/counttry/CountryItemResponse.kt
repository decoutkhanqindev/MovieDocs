package com.example.moviedocs.data.remote.response.moviedetails.counttry

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CountryItemResponse(
  @Json(name = "iso_3166_1")
  val iso31661: String, // AD
  @Json(name = "english_name")
  val englishName: String, // Andorra
  @Json(name = "native_name")
  val nativeName: String, // Andorra
)
