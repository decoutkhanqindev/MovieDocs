package com.example.moviedocs.data.repository.credits

import com.example.moviedocs.data.mapper.credits.toCreditListModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.credits.CreditListModel
import com.example.moviedocs.domain.repository.credits.CreditsRepository
import com.example.moviedocs.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CreditsRepositoryImpl @Inject constructor(
  private val apiService: ApiService,
  @AppDispatcher(DispatcherType.IO)
  private val ioDispatcher: CoroutineDispatcher
) : CreditsRepository {
  override suspend fun getCreditList(movieId: Int): Result<CreditListModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getCreditList(movieId).toCreditListModel()
    }
}