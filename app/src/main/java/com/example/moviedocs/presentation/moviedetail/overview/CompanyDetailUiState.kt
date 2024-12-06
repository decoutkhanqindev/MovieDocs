package com.example.moviedocs.presentation.moviedetail.overview

import com.example.moviedocs.domain.model.moviedetail.company.CompanyDetailModel
import com.example.moviedocs.domain.model.moviedetail.country.CountryItemModel

sealed interface CompanyDetailUiState {
  data object Loading : CompanyDetailUiState

  data class Error(val throwable: Throwable) : CompanyDetailUiState

  data class Success(
    val companyDetail: CompanyDetailModel,
    val countryList: List<CountryItemModel> = emptyList()
  ) : CompanyDetailUiState
}