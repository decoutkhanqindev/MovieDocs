package com.example.moviedocs.data.mapper.moviedetail.image

import com.example.moviedocs.data.remote.response.moviedetails.image.MediaListResponse
import com.example.moviedocs.domain.model.moviedetail.image.MediaListModel

fun MediaListResponse.toImageListModel(): MediaListModel =
  MediaListModel(
    id = id,
    backdrops = backdrops?.map { it.toImageItemModel() } ?: emptyList(),
    posters = posters?.map { it.toImageItemModel() } ?: emptyList(),
    logos = logos?.map { it.toImageItemModel() } ?: emptyList()
  )