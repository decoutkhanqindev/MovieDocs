package com.example.moviedocs.domain.repository.person

import com.example.moviedocs.domain.person.PersonDetailModel
import com.example.moviedocs.domain.person.PersonMovieCreditListModel

interface PersonRepository {
  suspend fun getPersonDetail(personId: Int): Result<PersonDetailModel>
  suspend fun getPersonMovieCreditList(personId: Int): Result<PersonMovieCreditListModel>
}