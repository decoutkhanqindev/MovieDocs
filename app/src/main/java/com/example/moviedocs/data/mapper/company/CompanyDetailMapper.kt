package com.example.moviedocs.data.mapper.company

import com.example.moviedocs.data.remote.response.company.CompanyDetailResponse
import com.example.moviedocs.domain.model.company.CompanyDetailModel

fun CompanyDetailResponse.toCompanyDetailModel(): CompanyDetailModel =
  CompanyDetailModel(
    id = id,
    description = description?.takeIf { it.isNotEmpty() } ?: "No description",
    headquarters = headquarters?.takeIf { it.isNotEmpty() } ?: "Unknown headquarters",
    homepage = homepage?.takeIf { it.isNotEmpty() } ?: "",
    logoPath = logoPath?.takeIf { it.isNotEmpty() } ?: "",
    name = name?.takeIf { it.isNotEmpty() } ?: "Unknown name",
    originCountry = originCountry?.takeIf { it.isNotEmpty() } ?: "Unknown origin country",
    parentCompany = parentCompany?.name?.takeIf { it.isNotEmpty() } ?: "No parent company"
  )
