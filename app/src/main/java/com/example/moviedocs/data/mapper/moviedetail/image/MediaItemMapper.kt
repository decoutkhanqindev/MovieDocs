package com.example.moviedocs.data.mapper.moviedetail.image

import com.example.moviedocs.data.remote.response.moviedetails.image.MediaItemResponse
import com.example.moviedocs.domain.model.moviedetail.image.MediaItemModel

fun MediaItemResponse.toImageItemModel(): MediaItemModel =
  MediaItemModel(
    aspectRatio = aspectRatio,
    filePath = filePath,
    height = height,
    iso6391 = iso6391.takeIf { !it.isNullOrBlank() } ?: "",
    voteAverage = voteAverage.takeIf { it != 0.0 } ?: 0.0,
    voteCount = voteCount.takeIf { it != 0 } ?: 0,
    width = width
  )