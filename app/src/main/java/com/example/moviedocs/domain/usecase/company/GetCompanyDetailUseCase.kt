package com.example.moviedocs.domain.usecase.company

import com.example.moviedocs.domain.model.company.CompanyDetailModel
import com.example.moviedocs.domain.repository.company.CompanyRepository
import javax.inject.Inject

class GetCompanyDetailUseCase @Inject constructor(
  private val repository: CompanyRepository,
) {
  suspend operator fun invoke(companyId: Int): Result<CompanyDetailModel> =
    repository.getCompanyDetail(companyId = companyId)
}