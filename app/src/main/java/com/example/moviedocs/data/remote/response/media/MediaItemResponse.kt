package com.example.moviedocs.data.remote.response.media


import com.squareup.moshi.Json

data class MediaItemResponse(
  @Json(name = "aspect_ratio")
  val aspectRatio: Double, // 1.778
  @Json(name = "file_path")
  val filePath: String, // /8PwntYBvxnaJq6R57UiXGNFsluN.jpg
  @Json(name = "height")
  val height: Int, // 1080
  @Json(name = "iso_639_1")
  val iso6391: String?, // pt
  @Json(name = "vote_average")
  val voteAverage: Double?, // 8.034
  @Json(name = "vote_count")
  val voteCount: Int?, // 5
  @Json(name = "width")
  val width: Int // 1920
)