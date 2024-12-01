package com.example.moviedocs.domain.usecase.moviedetail

import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel
import com.example.moviedocs.domain.repository.moviedetail.MovieDetailRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
  private val repository: MovieDetailRepository,
) {
  suspend operator fun invoke(movieId: Int): Result<MovieDetailModel> =
    repository.getMovieDetail(movieId = movieId)
}