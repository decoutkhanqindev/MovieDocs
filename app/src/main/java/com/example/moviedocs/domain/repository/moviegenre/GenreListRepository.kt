package com.example.moviedocs.domain.repository.moviegenre

import com.example.moviedocs.domain.model.moviegenre.GenreListModel

interface GenreListRepository {
  suspend fun getGenre() : Result<GenreListModel>
}