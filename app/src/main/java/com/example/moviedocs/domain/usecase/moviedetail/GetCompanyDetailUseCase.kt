package com.example.moviedocs.domain.usecase.moviedetail

import com.example.moviedocs.domain.model.moviedetail.company.CompanyDetailModel
import com.example.moviedocs.domain.repository.moviedetail.MovieDetailRepository
import javax.inject.Inject

class GetCompanyDetailUseCase @Inject constructor(
  private val repository: MovieDetailRepository,
) {
  suspend operator fun invoke(companyId: Int): Result<CompanyDetailModel> =
    repository.getCompanyDetail(companyId = companyId)
}