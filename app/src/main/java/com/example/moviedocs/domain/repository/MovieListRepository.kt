package com.example.moviedocs.domain.repository

import com.example.moviedocs.domain.model.list.MovieListModel

interface MovieListRepository {
  suspend fun getNowPlaying(page: Int): Result<MovieListModel>
  suspend fun getPopular(page: Int): Result<MovieListModel>
  suspend fun getUpcoming(page: Int): Result<MovieListModel>
  suspend fun getTopRated(page: Int): Result<MovieListModel>
}