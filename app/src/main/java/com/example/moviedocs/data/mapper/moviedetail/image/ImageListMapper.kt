package com.example.moviedocs.data.mapper.moviedetail.image

import com.example.moviedocs.data.remote.response.moviedetails.image.ImageListResponse
import com.example.moviedocs.domain.model.moviedetail.image.ImageListModel

fun ImageListResponse.toImageListModel(): ImageListModel =
  ImageListModel(
    id = id,
    backdrops = backdrops?.map { it.toImageItemModel() } ?: emptyList(),
    posters = posters?.map { it.toImageItemModel() } ?: emptyList(),
    logos = logos?.map { it.toImageItemModel() } ?: emptyList()
  )