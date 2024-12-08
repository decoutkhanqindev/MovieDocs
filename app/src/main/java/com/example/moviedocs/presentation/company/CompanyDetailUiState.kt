package com.example.moviedocs.presentation.company

import com.example.moviedocs.domain.model.company.CompanyDetailModel
import com.example.moviedocs.domain.model.country.CountryItemModel

sealed interface CompanyDetailUiState {
  data object Loading : CompanyDetailUiState

  data class Error(val throwable: Throwable) : CompanyDetailUiState

  data class Success(
    val companyDetail: CompanyDetailModel,
    val countryList: List<CountryItemModel> = emptyList()
  ) : CompanyDetailUiState
}