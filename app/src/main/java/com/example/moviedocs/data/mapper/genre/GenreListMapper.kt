package com.example.moviedocs.data.mapper.genre

import com.example.moviedocs.data.remote.response.genre.GenreListResponse
import com.example.moviedocs.domain.model.genre.GenreListModel

fun GenreListResponse.toGenreListModel(): GenreListModel =
  GenreListModel(genres = genres.map { it.toGenreItemModel() })