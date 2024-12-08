package com.example.moviedocs.domain.repository.country

import com.example.moviedocs.domain.model.country.CountryItemModel

interface CountryRepository {
  suspend fun getCountryList(): Result<List<CountryItemModel>>
}