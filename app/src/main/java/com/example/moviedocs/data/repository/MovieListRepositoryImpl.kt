package com.example.moviedocs.data.repository

import com.example.moviedocs.data.mapper.toMovieListModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.domain.repository.MovieListRepository
import com.example.moviedocs.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieListRepositoryImpl
@Inject constructor(
  private val apiService: ApiService,
  @AppDispatcher(DispatcherType.IO)
  private val ioDispatcher: CoroutineDispatcher
) : MovieListRepository {
  
  override suspend fun getMoviesNowPlaying(): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getMovieNowPlaying().results.map { it.toMovieListModel() }
    }
  
  override suspend fun getMoviesPopular(): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getMoviePopular().results.map { it.toMovieListModel() }
    }
  
  override suspend fun getMoviesTopRated(): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getMovieTopRated().results.map { it.toMovieListModel() }
    }
  
  override suspend fun getMoviesUpcoming(): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getMovieUpcoming().results.map { it.toMovieListModel() }
    }
}