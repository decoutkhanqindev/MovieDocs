package com.example.moviedocs.data.remote.response.company

import androidx.annotation.Keep
import com.example.moviedocs.data.remote.response.moviedetails.company.CompanyItemResponse
import com.squareup.moshi.Json

@Keep
data class CompanyDetailResponse(
  @Json(name = "description")
  val description: String?,
  @Json(name = "headquarters")
  val headquarters: String?,
  @Json(name = "homepage")
  val homepage: String?,
  @Json(name = "id")
  val id: Int,
  @Json(name = "logo_path")
  val logoPath: String?,
  @Json(name = "name")
  val name: String?,
  @Json(name = "origin_country")
  val originCountry: String?,
  @Json(name = "parent_company")
  val parentCompany: CompanyItemResponse?
)