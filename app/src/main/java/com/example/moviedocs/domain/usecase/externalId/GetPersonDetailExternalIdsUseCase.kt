package com.example.moviedocs.domain.usecase.externalId

import com.example.moviedocs.domain.externalId.ExternalIdsModel
import com.example.moviedocs.domain.repository.externalId.ExternalIdRepository
import javax.inject.Inject

class GetPersonDetailExternalIdsUseCase @Inject constructor(
  private val repository: ExternalIdRepository,
) {
  suspend operator fun invoke(personId: Int): Result<ExternalIdsModel> =
    repository.getPersonDetailExternalIds(personId = personId)
}