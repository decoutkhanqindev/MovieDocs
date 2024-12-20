package com.example.moviedocs.data.mapper.credits

import com.example.moviedocs.data.remote.response.credits.CastItemResponse
import com.example.moviedocs.domain.model.credits.CastItemModel

fun CastItemResponse.toCastItemModel(): CastItemModel =
  CastItemModel(
    castId = castId,
    character = character?.takeIf { it.isNotEmpty() } ?: "Unknown character",
    creditId = creditId,
    gender = if (gender == 1) "Female" else if (gender == 2) "Male" else "Unknown gender",
    id = id,
    knownForDepartment = knownForDepartment?.takeIf { it.isNotEmpty() } ?: "Unknown department",
    name = name?.takeIf { it.isNotEmpty() } ?: "Unknown name",
    originalName = originalName?.takeIf { it.isNotEmpty() } ?: "Unknown original name",
    popularity = popularity?.takeIf { it != 0.0 } ?: 0.0,
    profilePath = profilePath?.takeIf { it.isNotEmpty() } ?: ""
  )