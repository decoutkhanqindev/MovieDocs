package com.example.moviedocs.data.repository.moviedetail

import com.example.moviedocs.data.mapper.moviedetail.external.toExternalIdsModel
import com.example.moviedocs.data.mapper.moviedetail.toMovieDetailModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel
import com.example.moviedocs.domain.model.moviedetail.external.ExternalIdsModel
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
}