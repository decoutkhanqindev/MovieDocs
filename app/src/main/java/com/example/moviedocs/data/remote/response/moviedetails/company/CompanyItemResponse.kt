package com.example.moviedocs.data.remote.response.moviedetails.company

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CompanyItemResponse(
  @Json(name = "id")
  val id: Int, // 238902
  @Json(name = "logo_path")
  val logoPath: String?, // /jLAg5fOlAw1Jl8Q7WoyKxh1H22y.png
  @Json(name = "name")
  val name: String?, // Cineverse
//  @Json(name = "origin_country")
//  val originCountry: String, // US
)
