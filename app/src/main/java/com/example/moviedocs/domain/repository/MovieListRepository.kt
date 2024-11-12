package com.example.moviedocs.domain.repository

import com.example.moviedocs.domain.model.MovieModel

interface MovieListRepository {
  suspend fun getNowPlaying(page: Int): Result<List<MovieModel>>
  suspend fun getPopular(page: Int): Result<List<MovieModel>>
  suspend fun getUpcoming(page: Int): Result<List<MovieModel>>
  suspend fun getTopRated(page: Int): Result<List<MovieModel>>
}