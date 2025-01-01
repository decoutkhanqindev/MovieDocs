package com.example.moviedocs.data.remote.response.media


import com.squareup.moshi.Json

data class MediaListResponse(
  @Json(name = "backdrops")
  val backdrops: List<MediaItemResponse>?,
  @Json(name = "id")
  val id: Int, // 912649
  @Json(name = "logos")
  val logos: List<MediaItemResponse>?,
  @Json(name = "posters")
  val posters: List<MediaItemResponse>?
)