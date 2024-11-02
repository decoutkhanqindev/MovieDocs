package com.example.moviedocs.domain.repository

import com.example.moviedocs.domain.model.MovieModel

interface MovieListRepository {
  suspend fun getMoviesNowPlaying(): Result<List<MovieModel>>
  suspend fun getMoviesPopular(): Result<List<MovieModel>>
  suspend fun getMoviesTopRated(): Result<List<MovieModel>>
  suspend fun getMoviesUpcoming(): Result<List<MovieModel>>
}