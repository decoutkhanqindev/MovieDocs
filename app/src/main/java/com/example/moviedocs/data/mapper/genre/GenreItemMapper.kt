package com.example.moviedocs.data.mapper.genre

import com.example.moviedocs.data.remote.response.genre.GenreItemResponse
import com.example.moviedocs.domain.model.genre.GenreItemModel

fun GenreItemResponse.toGenreItemModel(): GenreItemModel =
  GenreItemModel(id = id, name = name?.takeIf { it.isNotEmpty() } ?: "No name")