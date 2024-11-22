package com.example.moviedocs.domain.usecase.list

import com.example.moviedocs.domain.model.list.MovieListModel
import com.example.moviedocs.domain.repository.list.MovieListRepository
import javax.inject.Inject

class GetUpcomingUseCase
@Inject constructor(private val repository: MovieListRepository) {
  suspend operator fun invoke(page: Int): Result<MovieListModel> =
    repository.getUpcoming(page = page)
}

