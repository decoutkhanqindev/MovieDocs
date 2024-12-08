package com.example.moviedocs.domain.usecase.country

import com.example.moviedocs.domain.model.country.CountryItemModel
import com.example.moviedocs.domain.repository.country.CountryRepository
import javax.inject.Inject

class GetCountryListUseCase @Inject constructor(
  private val repository: CountryRepository,
) {
  suspend operator fun invoke(): Result<List<CountryItemModel>> =
    repository.getCountryList()
}