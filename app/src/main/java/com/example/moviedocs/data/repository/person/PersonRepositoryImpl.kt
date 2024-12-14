package com.example.moviedocs.data.repository.person

import com.example.moviedocs.data.mapper.person.toPersonDetailModel
import com.example.moviedocs.data.mapper.person.toPersonMovieCreditListModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.person.PersonDetailModel
import com.example.moviedocs.domain.person.PersonMovieCreditListModel
import com.example.moviedocs.domain.repository.person.PersonRepository
import com.example.moviedocs.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
  private val apiService: ApiService,
  @AppDispatcher(DispatcherType.IO)
  private val ioDispatcher: CoroutineDispatcher
) : PersonRepository {
  override suspend fun getPersonDetail(personId: Int): Result<PersonDetailModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getPersonDetail(personId = personId).toPersonDetailModel()
    }

  override suspend fun getPersonMovieCreditList(personId: Int): Result<PersonMovieCreditListModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getPersonMovieCreditList(personId).toPersonMovieCreditListModel()
    }
}