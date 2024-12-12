package com.example.moviedocs.domain.usecase.genre

import com.example.moviedocs.domain.model.genre.GenreListModel
import com.example.moviedocs.domain.repository.genre.GenreRepository
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(
  private val repository: GenreRepository
) {
  suspend operator fun invoke(): Result<GenreListModel> = repository.getGenreList()
}