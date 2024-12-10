package com.example.moviedocs.data.mapper.credits

import com.example.moviedocs.data.remote.response.credits.CastItemResponse
import com.example.moviedocs.domain.model.credits.CastItemModel

fun CastItemResponse.toCastItemModel(): CastItemModel =
  CastItemModel(
    castId = castId,
    character = character?.takeIf { it.isNotEmpty() } ?: "No character",
    creditId = creditId,
    gender = if (gender == 1) "Female" else if (gender == 2) "Male" else "Unknown",
    id = id,
    knownForDepartment = knownForDepartment?.takeIf { it.isNotEmpty() } ?: "No department",
    name = name?.takeIf { it.isNotEmpty() } ?: "No name",
    originalName = originalName?.takeIf { it.isNotEmpty() } ?: "No original name",
    popularity = popularity?.takeIf { it != 0.0 } ?: 0.0,
    profilePath = profilePath?.takeIf { it.isNotEmpty() } ?: "No profile path"
  )