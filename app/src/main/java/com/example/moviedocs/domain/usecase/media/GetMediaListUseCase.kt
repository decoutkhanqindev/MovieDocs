package com.example.moviedocs.domain.usecase.media

import com.example.moviedocs.domain.model.media.MediaListModel
import com.example.moviedocs.domain.repository.media.MediaRepository
import javax.inject.Inject

class GetMediaListUseCase @Inject constructor(
  private val repository: MediaRepository,
) {
  suspend operator fun invoke(movieId: Int): Result<MediaListModel> =
    repository.getMediaList(movieId = movieId)
}