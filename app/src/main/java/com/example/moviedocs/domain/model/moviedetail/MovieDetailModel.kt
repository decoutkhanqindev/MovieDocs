package com.example.moviedocs.domain.model.moviedetail

import com.example.moviedocs.domain.model.moviedetail.company.CompanyItemModel
import com.example.moviedocs.domain.model.moviegenre.GenreItemModel

data class MovieDetailModel(
  val id: Int, // 1034541
  val posterPath: String,
  val title: String, // Terrifier 3
  val releaseDate: String, // 2024-10-09
  val runtime: Int, // 125
  val voteAverage: Double, // 6.9
  val voteCount: Int, // 1141
  val popularity: Double,
  val overview: String, // Five years after surviving Art the Clown's Halloween massacre, Sienna and Jonathan are still struggling to rebuild their shattered lives. As the holiday season approaches, they try to embrace the Christmas spirit and leave the horrors of the past behind. But just when they think they're safe, Art returns, determined to turn their holiday cheer into a new nightmare. The festive season quickly unravels as Art unleashes his twisted brand of terror, proving that no holiday is safe.
  val genres: List<GenreItemModel>,
  val status: String, // Released
  val originCountry: List<String>,
  val originalLanguage: String, // en
  val budget: Int, // 2000000
  val revenue: Int, // 87261211
  val productionCompanies: List<CompanyItemModel>,
  val homepage: String,
)
