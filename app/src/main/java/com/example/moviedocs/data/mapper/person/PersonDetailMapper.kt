package com.example.moviedocs.data.mapper.person

import com.example.moviedocs.data.remote.response.person.PersonDetailResponse
import com.example.moviedocs.domain.person.PersonDetailModel

fun PersonDetailResponse.toPersonDetailModel(): PersonDetailModel =
  PersonDetailModel(
    alsoKnownAs = alsoKnownAs?.map {
      it.takeIf { it?.isNotEmpty() == true } ?: "Unknown other names"
    } ?: emptyList(),
    biography = biography?.takeIf { it.isNotEmpty() } ?: "No biography",
    birthday = birthday?.takeIf { it.isNotEmpty() } ?: "",
    deathday = deathday?.takeIf { it.isNotEmpty() } ?: "Now",
    gender = if (gender == 1) "Female" else if (gender == 2) "Male" else "Unknown",
    homepage = homepage?.takeIf { it.isNotEmpty() } ?: "",
    id = id,
    knownForDepartment = knownForDepartment?.takeIf { it.isNotEmpty() } ?: "Unknown department",
    name = name?.takeIf { it.isNotEmpty() } ?: "Unknown name",
    placeOfBirth = placeOfBirth?.takeIf { it.isNotEmpty() } ?: "Unknown place of birth",
    popularity = popularity.takeIf { it != 0.0 } ?: 0.0,
    profilePath = profilePath?.takeIf { it.isNotEmpty() } ?: ""
  )