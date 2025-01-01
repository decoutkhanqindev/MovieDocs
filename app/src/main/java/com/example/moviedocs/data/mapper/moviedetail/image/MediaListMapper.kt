package com.example.moviedocs.data.mapper.moviedetail.image

import com.example.moviedocs.data.remote.response.media.MediaListResponse
import com.example.moviedocs.domain.model.media.MediaListModel

fun MediaListResponse.toMediaListModel(): MediaListModel =
  MediaListModel(
    id = id,
    backdrops = backdrops?.map { it.toMediaItemModel() } ?: emptyList(),
    posters = posters?.map { it.toMediaItemModel() } ?: emptyList(),
    logos = logos?.map { it.toMediaItemModel() } ?: emptyList()
  )