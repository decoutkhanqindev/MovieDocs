package com.example.moviedocs.domain.usecase.person

import com.example.moviedocs.domain.person.PersonMovieCreditListModel
import com.example.moviedocs.domain.repository.person.PersonRepository
import javax.inject.Inject

class GetPersonMovieCreditListUseCase @Inject constructor(
  private val repository: PersonRepository
) {
  suspend operator fun invoke(personId: Int): Result<PersonMovieCreditListModel> =
    repository.getPersonMovieCreditList(personId)
}