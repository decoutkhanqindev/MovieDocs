package com.example.moviedocs.domain.usecase.genre

import com.example.moviedocs.domain.model.genre.GenreListModel
import com.example.moviedocs.domain.repository.genre.GenreListRepository
import javax.inject.Inject

class GetGenreListUseCase
@Inject constructor(private val repository: GenreListRepository) {
  private suspend operator fun invoke(): Result<GenreListModel> = repository.getGenre()
}