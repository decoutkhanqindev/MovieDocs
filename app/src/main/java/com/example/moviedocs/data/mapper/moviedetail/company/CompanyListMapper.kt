package com.example.moviedocs.data.mapper.moviedetail.company

import com.example.moviedocs.data.remote.response.moviedetails.company.CompanyListResponse
import com.example.moviedocs.domain.model.moviedetail.company.CompanyListModel

fun CompanyListResponse.toCompanyListModel(): CompanyListModel = CompanyListModel(
  results = results.map { it.toCompanyItemModel() }
)