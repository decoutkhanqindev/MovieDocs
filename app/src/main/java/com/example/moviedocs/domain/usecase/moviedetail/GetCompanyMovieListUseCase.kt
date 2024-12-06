package com.example.moviedocs.domain.usecase.moviedetail

import com.example.moviedocs.domain.model.movielist.MovieListModel
import com.example.moviedocs.domain.repository.moviedetail.MovieDetailRepository
import javax.inject.Inject

class GetCompanyMovieListUseCase @Inject constructor(
  private val repository: MovieDetailRepository
) {
  suspend operator fun invoke(page: Int, companyId: Int): Result<MovieListModel> =
    repository.getCompanyMovieList(page = page, companyId = companyId)
}

