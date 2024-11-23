package com.example.moviedocs.domain.usecase.moviegenre

import com.example.moviedocs.domain.model.moviegenre.GenreListModel
import com.example.moviedocs.domain.repository.moviegenre.GenreListRepository
import javax.inject.Inject

class GetGenreListUseCase
@Inject constructor(private val repository: GenreListRepository) {
  suspend operator fun invoke(): Result<GenreListModel> = repository.getGenre()
}