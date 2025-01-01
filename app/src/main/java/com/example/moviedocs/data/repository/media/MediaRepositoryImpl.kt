package com.example.moviedocs.data.repository.media

import com.example.moviedocs.data.mapper.moviedetail.image.toMediaListModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.media.MediaListModel
import com.example.moviedocs.domain.repository.media.MediaRepository
import com.example.moviedocs.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
  private val apiService: ApiService,
  @AppDispatcher(DispatcherType.IO)
  private val ioDispatcher: CoroutineDispatcher,
) : MediaRepository {

  override suspend fun getMediaList(movieId: Int): Result<MediaListModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getMediaList(movieId = movieId).toMediaListModel()
    }
}