package com.example.moviedocs.domain.repository.company

import com.example.moviedocs.domain.model.company.CompanyDetailModel
import com.example.moviedocs.domain.model.movielist.MovieListModel

interface CompanyRepository {
  suspend fun getCompanyDetail(companyId: Int): Result<CompanyDetailModel>
  suspend fun getCompanyMovieList(page: Int, companyId: Int): Result<MovieListModel>
}