package com.example.moviedocs.data.remote.response.person

import com.example.moviedocs.data.remote.response.movielist.MovieItemResponse
import com.squareup.moshi.Json

data class PersonMovieCreditListResponse(
  @Json(name = "cast")
  val cast: List<MovieItemResponse>,
  @Json(name = "crew")
  val crew: List<MovieItemResponse>,
  @Json(name = "id")
  val id: Int // person id
)