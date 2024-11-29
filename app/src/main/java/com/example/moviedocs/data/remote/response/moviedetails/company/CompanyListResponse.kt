package com.example.moviedocs.data.remote.response.moviedetails.company

import androidx.annotation.Keep

@Keep
data class CompanyListResponse(
  val results: List<CompanyItemResponse>,
)
