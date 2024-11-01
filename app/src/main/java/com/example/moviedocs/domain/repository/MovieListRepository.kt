package com.example.moviedocs.domain.repository

import com.example.moviedocs.domain.model.MovieModel

interface MovieListRepository {
  suspend fun getMoviesNowPlaying(): List<MovieModel>
  suspend fun getMoviesPopular(): List<MovieModel>
  suspend fun getMoviesTopRated(): List<MovieModel>
  suspend fun getMoviesUpcoming(): List<MovieModel>
}