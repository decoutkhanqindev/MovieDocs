package com.example.moviedocs.data.mapper.credits

import com.example.moviedocs.data.remote.response.credits.CrewItemResponse
import com.example.moviedocs.domain.model.credits.CrewItemModel

fun CrewItemResponse.toCrewItemModel(): CrewItemModel =
  CrewItemModel(
    creditId = creditId,
    department = department?.takeIf { it.isNotEmpty() } ?: "Unknown department",
    gender = if (gender == 1) "Female" else if (gender == 2) "Male" else "Unknown",
    id = id,
    job = job?.takeIf { it.isNotEmpty() } ?: "No job",
    knownForDepartment = knownForDepartment?.takeIf { it.isNotEmpty() } ?: "Unknown department",
    name = name?.takeIf { it.isNotEmpty() } ?: "Unknown name",
    originalName = originalName?.takeIf { it.isNotEmpty() } ?: "Unknown original name",
    popularity = popularity?.takeIf { it != 0.0 } ?: 0.0,
    profilePath = profilePath?.takeIf { it.isNotEmpty() } ?: ""
  )