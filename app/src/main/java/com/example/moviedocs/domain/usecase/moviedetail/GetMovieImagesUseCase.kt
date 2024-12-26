package com.example.moviedocs.domain.usecase.moviedetail

import com.example.moviedocs.domain.model.moviedetail.image.ImageListModel
import com.example.moviedocs.domain.repository.moviedetail.MovieDetailRepository
import javax.inject.Inject

class GetMovieImagesUseCase @Inject constructor(
  private val repository: MovieDetailRepository,
) {
  suspend operator fun invoke(movieId: Int): Result<ImageListModel> =
    repository.getMovieImages(movieId = movieId)
}