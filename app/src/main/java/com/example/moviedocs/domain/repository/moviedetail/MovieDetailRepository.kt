package com.example.moviedocs.domain.repository.moviedetail

import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel
import com.example.moviedocs.domain.model.moviedetail.image.ImageListModel

interface MovieDetailRepository {
  suspend fun getMovieDetail(movieId: Int): Result<MovieDetailModel>
  suspend fun getMovieImages(movieId: Int): Result<ImageListModel>
}