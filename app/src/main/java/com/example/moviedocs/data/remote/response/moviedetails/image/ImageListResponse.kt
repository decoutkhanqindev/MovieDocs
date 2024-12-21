package com.example.moviedocs.data.remote.response.moviedetails.image


import com.squareup.moshi.Json

data class ImageListResponse(
  @Json(name = "backdrops")
  val backdrops: List<ImageItemResponse>?,
  @Json(name = "id")
  val id: Int, // 912649
  @Json(name = "logos")
  val logos: List<ImageItemResponse>?,
  @Json(name = "posters")
  val posters: List<ImageItemResponse>?
)