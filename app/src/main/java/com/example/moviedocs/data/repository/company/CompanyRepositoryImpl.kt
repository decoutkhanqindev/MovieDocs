package com.example.moviedocs.data.repository.company

import com.example.moviedocs.data.mapper.company.toCompanyDetailModel
import com.example.moviedocs.data.mapper.movielist.toMovieListModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.company.CompanyDetailModel
import com.example.moviedocs.domain.model.movielist.MovieListModel
import com.example.moviedocs.domain.repository.company.CompanyRepository
import com.example.moviedocs.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
  private val apiService: ApiService,
  @AppDispatcher(DispatcherType.IO)
  private val ioDispatcher: CoroutineDispatcher,
) : CompanyRepository {

  override suspend fun getCompanyDetail(companyId: Int): Result<CompanyDetailModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getCompanyDetail(companyId = companyId).toCompanyDetailModel()
    }

  override suspend fun getCompanyMovieList(page: Int, companyId: Int): Result<MovieListModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getCompanyMovieList(page = page, companyId = companyId).toMovieListModel()
    }
}