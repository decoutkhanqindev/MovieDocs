package com.example.moviedocs.domain.usecase.movielist

import com.example.moviedocs.domain.model.movielist.MovieListModel
import com.example.moviedocs.domain.repository.movielist.MovieListRepository
import javax.inject.Inject

class GetUpcomingUseCase @Inject constructor(
  private val repository: MovieListRepository,
) {
  suspend operator fun invoke(page: Int): Result<MovieListModel> =
    repository.getUpcoming(page = page)
}

