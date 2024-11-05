package com.example.moviedocs.domain.repository

import com.example.moviedocs.domain.model.MovieModel

interface MoviesRepository {
  suspend fun getNowPlayingMovies(page: Int): Result<List<MovieModel>>
  suspend fun getPopularMovies(page: Int): Result<List<MovieModel>>
  suspend fun getUpcomingMovies(page: Int): Result<List<MovieModel>>
}