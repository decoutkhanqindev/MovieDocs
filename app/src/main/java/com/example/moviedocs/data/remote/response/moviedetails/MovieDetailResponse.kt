package com.example.moviedocs.data.remote.response.moviedetails


import androidx.annotation.Keep
import com.example.moviedocs.data.remote.response.moviedetails.company.CompanyListResponse
import com.example.moviedocs.data.remote.response.moviegenre.GenreListResponse
import com.squareup.moshi.Json

@Keep
data class MovieDetailResponse(
    @Json(name = "adult")
    val adult: Boolean, // false
    @Json(name = "backdrop_path")
    val backdropPath: String, // /18TSJF1WLA4CkymvVUcKDBwUJ9F.jpg
//    @Json(name = "belongs_to_collection")
//    val belongsToCollection: BelongsToCollection,
    @Json(name = "budget")
    val budget: Int, // 2000000
    @Json(name = "genres")
    val genres: GenreListResponse,
    @Json(name = "homepage")
    val homepage: String, // https://terrifier3.com/
    @Json(name = "id")
    val id: Int, // 1034541
    @Json(name = "imdb_id")
    val imdbId: String, // tt27911000
    @Json(name = "origin_country")
    val originCountry: List<String>,
    @Json(name = "original_language")
    val originalLanguage: String, // en
    @Json(name = "original_title")
    val originalTitle: String, // Terrifier 3
    @Json(name = "overview")
    val overview: String, // Five years after surviving Art the Clown's Halloween massacre, Sienna and Jonathan are still struggling to rebuild their shattered lives. As the holiday season approaches, they try to embrace the Christmas spirit and leave the horrors of the past behind. But just when they think they're safe, Art returns, determined to turn their holiday cheer into a new nightmare. The festive season quickly unravels as Art unleashes his twisted brand of terror, proving that no holiday is safe.
    @Json(name = "popularity")
    val popularity: Double, // 1072.453
    @Json(name = "poster_path")
    val posterPath: String, // /l1175hgL5DoXnqeZQCcU3eZIdhX.jpg
    @Json(name = "production_companies")
    val productionCompanies: CompanyListResponse,
//    @Json(name = "production_countries")
//    val productionCountries: List<ProductionCountry>,
    @Json(name = "release_date")
    val releaseDate: String, // 2024-10-09
    @Json(name = "revenue")
    val revenue: Int, // 87261211
    @Json(name = "runtime")
    val runtime: Int, // 125
//    @Json(name = "spoken_languages")
//    val spokenLanguages: List<SpokenLanguage>,
    @Json(name = "status")
    val status: String, // Released
    @Json(name = "tagline")
    val tagline: String, // You better not shout, you better not cry
    @Json(name = "title")
    val title: String, // Terrifier 3
    @Json(name = "video")
    val video: Boolean, // false
    @Json(name = "vote_average")
    val voteAverage: Double, // 6.9
    @Json(name = "vote_count")
    val voteCount: Int, // 1141
)