package com.example.moviedocs.domain.usecase.list

import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.domain.repository.MovieListRepository
import javax.inject.Inject

class GetMovieListPopularUseCase
@Inject constructor(private val repository: MovieListRepository) {
  suspend operator fun invoke(): Result<List<MovieModel>> = repository.getMoviesPopular()
}