package com.example.moviedocs.data.remote.response.movielist

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class MovieListResponse(
  @Json(name = "page")
  val page: Int, // 1
  @Json(name = "results")
  val results: List<MovieItemResponse>?,
  @Json(name = "total_pages")
  val totalPages: Int?, // 46880
  @Json(name = "total_results")
  val totalResults: Int?, // 937587
)