package com.example.moviedocs.domain.repository.company

import com.example.moviedocs.domain.model.company.CompanyDetailModel

interface CompanyRepository {
  suspend fun getCompanyDetail(companyId: Int): Result<CompanyDetailModel>
}