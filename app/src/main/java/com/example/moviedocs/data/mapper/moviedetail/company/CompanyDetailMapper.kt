package com.example.moviedocs.data.mapper.moviedetail.company

import com.example.moviedocs.data.remote.response.moviedetails.company.CompanyDetailResponse
import com.example.moviedocs.domain.model.moviedetail.company.CompanyDetailModel

fun CompanyDetailResponse.toCompanyDetailModel(): CompanyDetailModel =
  CompanyDetailModel(
    id = id,
    description = description ?: "No Description",
    headquarters = headquarters ?: "No Headquarters",
    homepage = homepage ?: "No Homepage",
    logoPath = logoPath ?: "No Logo",
    name = name ?: "No Name",
    originCountry = originCountry ?: "No Origin Country",
    parentCompany = parentCompany ?: "No Parent Company"
  )