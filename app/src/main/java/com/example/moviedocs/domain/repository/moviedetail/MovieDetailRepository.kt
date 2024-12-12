package com.example.moviedocs.domain.repository.moviedetail

import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel

interface MovieDetailRepository {
  suspend fun getMovieDetail(movieId: Int): Result<MovieDetailModel>
}