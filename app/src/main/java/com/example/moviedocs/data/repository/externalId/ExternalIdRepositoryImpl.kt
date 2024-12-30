package com.example.moviedocs.data.repository.externalId

import com.example.moviedocs.data.mapper.externalId.toExternalIdsModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.externalId.ExternalIdsModel
import com.example.moviedocs.domain.repository.externalId.ExternalIdRepository
import com.example.moviedocs.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ExternalIdRepositoryImpl @Inject constructor(
  private val apiService: ApiService,
  @AppDispatcher(DispatcherType.IO)
  private val ioDispatcher: CoroutineDispatcher,
) : ExternalIdRepository {

  override suspend fun getMovieDetailExternalIds(movieId: Int): Result<ExternalIdsModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getMovieDetailExternalIds(movieId = movieId).toExternalIdsModel()
    }

  override suspend fun getPersonDetailExternalIds(personId: Int): Result<ExternalIdsModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getPersonDetailExternalIds(personId = personId).toExternalIdsModel()
    }
}