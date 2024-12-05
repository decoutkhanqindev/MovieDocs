package com.example.moviedocs.domain.model.moviedetail.company

data class CompanyDetailModel(
  val description: String,
  val headquarters: String,
  val homepage: String,
  val id: Int,
  val logoPath: String,
  val name: String,
  val originCountry: String,
  val parentCompany: String
)
