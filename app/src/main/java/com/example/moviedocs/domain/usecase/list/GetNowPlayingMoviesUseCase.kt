package com.example.moviedocs.domain.usecase.list

import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.domain.repository.MoviesRepository
import javax.inject.Inject

class GetNowPlayingMoviesUseCase
@Inject constructor(private val repository: MoviesRepository) {
  suspend operator fun invoke(page: Int): Result<List<MovieModel>> =
    repository.getNowPlayingMovies(page = page)
}

