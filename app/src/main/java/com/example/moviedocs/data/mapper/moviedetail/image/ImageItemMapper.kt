package com.example.moviedocs.data.mapper.moviedetail.image

import com.example.moviedocs.data.remote.response.moviedetails.image.ImageItemResponse
import com.example.moviedocs.domain.model.moviedetail.image.ImageItemModel

fun ImageItemResponse.toImageItemModel(): ImageItemModel =
  ImageItemModel(
    aspectRatio = aspectRatio,
    filePath = filePath,
    height = height,
    iso6391 = iso6391.takeIf { !it.isNullOrBlank() } ?: "",
    voteAverage = voteAverage.takeIf { it != 0.0 } ?: 0.0,
    voteCount = voteCount.takeIf { it != 0 } ?: 0,
    width = width
  )