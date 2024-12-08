package com.example.moviedocs.domain.usecase.movielist

import com.example.moviedocs.domain.model.movielist.MovieListModel
import com.example.moviedocs.domain.repository.company.CompanyRepository
import javax.inject.Inject

class GetCompanyMovieListUseCase @Inject constructor(
  private val repository: CompanyRepository
) {
  suspend operator fun invoke(page: Int, companyId: Int): Result<MovieListModel> =
    repository.getCompanyMovieList(page = page, companyId = companyId)
}

