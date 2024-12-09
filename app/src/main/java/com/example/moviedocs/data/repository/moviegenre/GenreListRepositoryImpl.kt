package com.example.moviedocs.data.repository.moviegenre

import com.example.moviedocs.data.mapper.moviegenre.toGenreListModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.moviegenre.GenreListModel
import com.example.moviedocs.domain.repository.moviegenre.GenreListRepository
import com.example.moviedocs.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GenreListRepositoryImpl @Inject constructor(
  private val apiService: ApiService,
  @AppDispatcher(DispatcherType.IO)
  private val ioDispatcher: CoroutineDispatcher,
) : GenreListRepository {
  
  override suspend fun getGenreList(): Result<GenreListModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getGenreList().toGenreListModel()
    }
}