package com.example.moviedocs.data.repository.moviedetail

import com.example.moviedocs.data.mapper.moviedetail.country.toCountryItemModel
import com.example.moviedocs.data.mapper.moviedetail.external.toExternalIdsModel
import com.example.moviedocs.data.mapper.moviedetail.language.toLanguageItemModel
import com.example.moviedocs.data.mapper.moviedetail.toMovieDetailModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel
import com.example.moviedocs.domain.model.moviedetail.country.CountryItemModel
import com.example.moviedocs.domain.model.moviedetail.external.ExternalIdsModel
import com.example.moviedocs.domain.model.moviedetail.language.LanguageItemModel
import com.example.moviedocs.domain.repository.moviedetail.MovieDetailRepository
import com.example.moviedocs.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
  private val apiService: ApiService,
  @AppDispatcher(DispatcherType.IO)
  private val ioDispatcher: CoroutineDispatcher,
) : MovieDetailRepository {
  
  override suspend fun getMovieDetail(movieId: Int): Result<MovieDetailModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getMovieDetail(movieId = movieId).toMovieDetailModel()
    }
  
  override suspend fun getExternalIds(movieId: Int): Result<ExternalIdsModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getExternalIds(movieId = movieId).toExternalIdsModel()
    }
  
  override suspend fun getCountryList(): Result<List<CountryItemModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getCountryList().map { it.toCountryItemModel() }
    }
  
  override suspend fun getLanguageList(): Result<List<LanguageItemModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getLanguageList().map { it.toLanguageItemModel() }
    }
}