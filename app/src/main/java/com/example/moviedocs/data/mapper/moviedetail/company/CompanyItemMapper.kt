package com.example.moviedocs.data.mapper.moviedetail.company

import com.example.moviedocs.data.remote.response.moviedetails.company.CompanyItemResponse
import com.example.moviedocs.domain.model.moviedetail.company.CompanyItemModel

fun CompanyItemResponse.toCompanyItemModel(): CompanyItemModel =
  CompanyItemModel(
    id = id,
    logoPath = logoPath?.takeIf { it.isNotEmpty() } ?: "",
    name = name?.takeIf { it.isNotEmpty() } ?: "Unknown company"
  )