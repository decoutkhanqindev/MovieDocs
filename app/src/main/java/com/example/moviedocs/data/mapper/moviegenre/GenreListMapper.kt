package com.example.moviedocs.data.mapper.moviegenre

import com.example.moviedocs.data.remote.response.moviegenre.GenreListResponse
import com.example.moviedocs.domain.model.moviegenre.GenreListModel

fun GenreListResponse.toGenreListModel(): GenreListModel =
  GenreListModel(genres = genres?.map { it.toGenreItemModel() } ?: emptyList())