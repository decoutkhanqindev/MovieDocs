package com.example.moviedocs.domain.usecase.person

import com.example.moviedocs.domain.model.person.PersonDetailModel
import com.example.moviedocs.domain.repository.person.PersonRepository
import javax.inject.Inject

class GetPersonDetailUseCase @Inject constructor(
  private val repository: PersonRepository
) {
  suspend operator fun invoke(personId: Int): Result<PersonDetailModel> =
    repository.getPersonDetail(personId)
}