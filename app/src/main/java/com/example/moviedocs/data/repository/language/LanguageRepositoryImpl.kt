package com.example.moviedocs.data.repository.language

import com.example.moviedocs.data.mapper.language.toLanguageItemModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.language.LanguageItemModel
import com.example.moviedocs.domain.repository.language.LanguageRepository
import com.example.moviedocs.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
  private val apiService: ApiService,
  @AppDispatcher(DispatcherType.IO)
  private val ioDispatcher: CoroutineDispatcher
) : LanguageRepository {
  override suspend fun getLanguageList(): Result<List<LanguageItemModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getLanguageList().map { it.toLanguageItemModel() }
    }
}