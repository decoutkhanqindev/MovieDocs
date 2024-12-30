package com.example.moviedocs.domain.usecase.externalId

import com.example.moviedocs.domain.model.externalId.ExternalIdsModel
import com.example.moviedocs.domain.repository.externalId.ExternalIdRepository
import javax.inject.Inject

class GetMovieDetailExternalIdsUseCase @Inject constructor(
  private val repository: ExternalIdRepository,
) {
  suspend operator fun invoke(movieId: Int): Result<ExternalIdsModel> =
    repository.getMovieDetailExternalIds(movieId = movieId)
}