package com.example.moviedocs.data.mapper.moviedetail.company

import com.example.moviedocs.data.remote.response.moviedetails.company.CompanyDetailResponse
import com.example.moviedocs.domain.model.moviedetail.company.CompanyDetailModel

fun CompanyDetailResponse.toCompanyDetailModel(): CompanyDetailModel =
  CompanyDetailModel(
    id = id,
    description = if (description.equals("")) "No description" else description.toString(),
    headquarters = headquarters ?: "No headquarters",
    homepage = homepage ?: "No homepage",
    logoPath = logoPath ?: "No logo",
    name = name ?: "No name",
    originCountry = originCountry ?: "No origin country",
    parentCompany = parentCompany?: "No parent company"
  )