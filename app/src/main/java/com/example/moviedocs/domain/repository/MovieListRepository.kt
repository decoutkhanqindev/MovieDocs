package com.example.moviedocs.domain.repository

import com.example.moviedocs.domain.model.MovieModel

interface MovieListRepository {
  suspend fun getMoviesNowPlaying(page: Int): Result<List<MovieModel>>
  suspend fun getMoviesPopular(page: Int): Result<List<MovieModel>>
  suspend fun getMoviesTopRated(page: Int): Result<List<MovieModel>>
  suspend fun getMoviesUpcoming(page: Int): Result<List<MovieModel>>
}