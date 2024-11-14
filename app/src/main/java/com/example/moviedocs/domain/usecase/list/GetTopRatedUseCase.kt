package com.example.moviedocs.domain.usecase.list

import com.example.moviedocs.domain.model.list.MovieListModel
import com.example.moviedocs.domain.repository.MovieListRepository
import javax.inject.Inject

class GetTopRatedUseCase
@Inject constructor(private val repository: MovieListRepository) {
  suspend operator fun invoke(page: Int): Result<MovieListModel> =
    repository.getTopRated(page = page)
}

