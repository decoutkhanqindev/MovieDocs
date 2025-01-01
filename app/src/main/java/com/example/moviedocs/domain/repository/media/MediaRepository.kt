package com.example.moviedocs.domain.repository.media

import com.example.moviedocs.domain.model.media.MediaListModel

interface MediaRepository {
  suspend fun getMediaList(movieId: Int): Result<MediaListModel>
}