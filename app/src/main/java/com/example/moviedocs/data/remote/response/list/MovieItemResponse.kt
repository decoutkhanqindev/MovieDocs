package com.example.moviedocs.data.remote.response.list

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class MovieItemResponse(
  @Json(name = "adult")
  val adult: Boolean?, // false
  @Json(name = "backdrop_path")
  val backdropPath: String?, // /3V4kLQg0kSqPLctI5ziYWabAZYF.jpg
  @Json(name = "genre_ids")
  val genreIds: List<Int>?,
  @Json(name = "id")
  val id: Int, // 912649
  @Json(name = "original_language")
  val originalLanguage: String?, // en
  @Json(name = "original_title")
  val originalTitle: String?, // Venom: The Last Dance
  @Json(name = "overview")
  val overview: String?, // Eddie and Venom are on the run. Hunted by both of their worlds and with the net closing in, the duo are forced into a devastating decision that will bring the curtains down on Venom and Eddie's last dance.
  @Json(name = "popularity")
  val popularity: Double?, // 5691.793
  @Json(name = "poster_path")
  val posterPath: String?, // /k42Owka8v91trK1qMYwCQCNwJKr.jpg
  @Json(name = "release_date")
  val releaseDate: String?, // 2024-10-22
  @Json(name = "title")
  val title: String?, // Venom: The Last Dance
  @Json(name = "video")
  val video: Boolean?, // false
  @Json(name = "vote_average")
  val voteAverage: Double?, // 6.7
  @Json(name = "vote_count")
  val voteCount: Int? // 357
)