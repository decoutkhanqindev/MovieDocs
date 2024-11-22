package com.example.moviedocs.domain.repository.genre

import com.example.moviedocs.domain.model.genre.GenreListModel

interface GenreListRepository {
  suspend fun getGenre() : Result<GenreListModel>
}