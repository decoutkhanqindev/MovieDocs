package com.example.moviedocs.data.repository.country

import com.example.moviedocs.data.mapper.country.toCountryItemModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.country.CountryItemModel
import com.example.moviedocs.domain.repository.country.CountryRepository
import com.example.moviedocs.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
  private val apiService: ApiService,
  @AppDispatcher(DispatcherType.IO)
  private val ioDispatcher: CoroutineDispatcher
) : CountryRepository {
  override suspend fun getCountryList(): Result<List<CountryItemModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getCountryList().map { it.toCountryItemModel() }
    }
}