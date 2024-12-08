package com.example.moviedocs.data.mapper.company

import com.example.moviedocs.data.remote.response.company.CompanyDetailResponse
import com.example.moviedocs.domain.model.company.CompanyDetailModel

fun CompanyDetailResponse.toCompanyDetailModel(): CompanyDetailModel =
  CompanyDetailModel(
    id = id,
    description = description?.takeIf { it.isNotEmpty() } ?: "No description",
    headquarters = headquarters?.takeIf { it.isNotEmpty() } ?: "No headquarters",
    homepage = homepage?.takeIf { it.isNotEmpty() } ?: "No homepage",
    logoPath = logoPath?.takeIf { it.isNotEmpty() } ?: "No logo",
    name = name?.takeIf { it.isNotEmpty() } ?: "No name",
    originCountry = originCountry?.takeIf { it.isNotEmpty() } ?: "No origin country",
    parentCompany = parentCompany?.name?.takeIf { it.isNotEmpty() } ?: "No parent company"
  )
