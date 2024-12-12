package com.example.moviedocs.domain.repository.externalId

import com.example.moviedocs.domain.externalId.ExternalIdsModel

interface ExternalIdRepository {
  suspend fun getMovieDetailExternalIds(movieId: Int): Result<ExternalIdsModel>
  suspend fun getPersonDetailExternalIds(personId: Int): Result<ExternalIdsModel>
}