package com.example.moviedocs.data.remote.externalId

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ExternalIdsResponse(
  @Json(name = "id")
  val id: Int, // 1034541
//    @Json(name = "imdb_id")
//    val imdbId: String, // tt27911000
//    @Json(name = "wikidata_id")
//    val wikidataId: String, // Q119821866
  @Json(name = "facebook_id")
  val facebookId: String?, // TerrifierFilm
  @Json(name = "instagram_id")
  val instagramId: String?, // officialterrifier3
  @Json(name = "twitter_id")
  val twitterId: String?, // terrifierfilm
  @Json(name = "tiktok_id")
  val tiktokId: String? // terrifierfilm
)