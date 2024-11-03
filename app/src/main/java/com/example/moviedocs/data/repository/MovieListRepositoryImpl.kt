package com.example.moviedocs.data.repository

import com.example.moviedocs.data.mapper.toMovieModel
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
  @AppDispatcher(DispatcherType.IO) private val ioDispatcher: CoroutineDispatcher
) : MovieListRepository {
  
  override suspend fun getMoviesNowPlaying(page: Int): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getMovieNowPlaying(page).results.map { it.toMovieModel() }
    }
  
  override suspend fun getMoviesPopular(page: Int): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getMoviePopular(page).results.map { it.toMovieModel() }
    }
  
  override suspend fun getMoviesTopRated(page: Int): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getMovieTopRated(page).results.map { it.toMovieModel() }
    }
  
  override suspend fun getMoviesUpcoming(page: Int): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getMovieUpcoming(page).results.map { it.toMovieModel() }
    }
}