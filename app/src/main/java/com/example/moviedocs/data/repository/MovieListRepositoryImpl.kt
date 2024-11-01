package com.example.moviedocs.data.repository

import com.example.moviedocs.data.mapper.toMovieListModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.domain.repository.MovieListRepository
import javax.inject.Inject

class MovieListRepositoryImpl
@Inject constructor(private val apiService: ApiService) : MovieListRepository {
  
  override suspend fun getMoviesNowPlaying(): List<MovieModel> =
    apiService.getMovieNowPlaying().map { it.toMovieListModel() }
  
  override suspend fun getMoviesPopular(): List<MovieModel> =
    apiService.getMoviePopular().map { it.toMovieListModel() }
  
  override suspend fun getMoviesTopRated(): List<MovieModel> =
    apiService.getMovieTopRated().map { it.toMovieListModel() }
  
  override suspend fun getMoviesUpcoming(): List<MovieModel> =
    apiService.getMovieUpcoming().map { it.toMovieListModel() }
}