package com.example.moviedocs.data.mapper.person

import com.example.moviedocs.data.remote.response.person.PersonDetailResponse
import com.example.moviedocs.domain.model.person.PersonDetailModel

fun PersonDetailResponse.toPersonDetailModel(): PersonDetailModel =
  PersonDetailModel(
    alsoKnownAs = alsoKnownAs?.takeIf { it.isNotEmpty() } ?: listOf<String>("Unknown other names"),
    biography = biography?.takeIf { it.isNotEmpty() } ?: "No biography",
    birthday = birthday?.takeIf { it.isNotEmpty() } ?: "",
    deathday = deathday?.takeIf { it.isNotEmpty() } ?: "",
    gender = if (gender == 1) "Female" else if (gender == 2) "Male" else "Unknown gender",
    homepage = homepage?.takeIf { it.isNotEmpty() } ?: "",
    id = id,
    knownForDepartment = knownForDepartment?.takeIf { it.isNotEmpty() } ?: "Unknown department",
    name = name?.takeIf { it.isNotEmpty() } ?: "Unknown name",
    placeOfBirth = placeOfBirth?.takeIf { it.isNotEmpty() } ?: "Unknown place of birth",
    popularity = popularity.takeIf { it != 0.0 } ?: 0.0,
    profilePath = profilePath?.takeIf { it.isNotEmpty() } ?: ""
  )