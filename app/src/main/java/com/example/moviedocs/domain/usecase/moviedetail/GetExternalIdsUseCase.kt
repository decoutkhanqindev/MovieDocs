package com.example.moviedocs.domain.usecase.moviedetail

import com.example.moviedocs.domain.model.moviedetail.external.ExternalIdsModel
import com.example.moviedocs.domain.repository.moviedetail.MovieDetailRepository
import javax.inject.Inject

class GetExternalIdsUseCase @Inject constructor(
  private val repository: MovieDetailRepository,
) {
  suspend operator fun invoke(movieId: Int): Result<ExternalIdsModel> =
    repository.getExternalIds(movieId = movieId)
}