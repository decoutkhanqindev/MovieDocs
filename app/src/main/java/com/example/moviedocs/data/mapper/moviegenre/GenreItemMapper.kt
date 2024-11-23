package com.example.moviedocs.data.mapper.moviegenre

import com.example.moviedocs.data.remote.response.moviegenre.GenreItemResponse
import com.example.moviedocs.domain.model.moviegenre.GenreItemModel

fun GenreItemResponse.toGenreItemModel(): GenreItemModel =
  GenreItemModel(id = id, name = name)