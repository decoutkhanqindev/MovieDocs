package com.example.moviedocs.domain.usecase.moviedetail

import com.example.moviedocs.domain.model.moviedetail.country.CountryItemModel
import com.example.moviedocs.domain.repository.moviedetail.MovieDetailRepository
import javax.inject.Inject

class GetCountryListUseCase @Inject constructor(
  private val repository: MovieDetailRepository,
) {
  suspend operator fun invoke(): Result<List<CountryItemModel>> =
    repository.getCountryList()
}