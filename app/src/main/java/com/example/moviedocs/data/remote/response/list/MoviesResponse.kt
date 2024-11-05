package com.example.moviedocs.data.remote.response.list

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class MoviesResponse(
  @Json(name = "page")
  val page: Int, // 1
  @Json(name = "results")
  val results: List<MovieResponse>,
  @Json(name = "total_pages")
  val totalPages: Int, // 46880
  @Json(name = "total_results")
  val totalResults: Int // 937587
)