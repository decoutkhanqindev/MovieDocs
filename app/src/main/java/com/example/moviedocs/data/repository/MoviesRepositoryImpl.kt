package com.example.moviedocs.data.repository

import com.example.moviedocs.data.mapper.toMovieModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.domain.repository.MoviesRepository
import com.example.moviedocs.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MoviesRepositoryImpl
@Inject constructor(
  private val apiService: ApiService,
  @AppDispatcher(DispatcherType.IO) private val ioDispatcher: CoroutineDispatcher
) : MoviesRepository {
  
  override suspend fun getNowPlayingMovies(page: Int): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getNowPlayingMovies(page).results.map { it.toMovieModel() }
    }
  
  override suspend fun getPopularMovies(page: Int): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getPopularMovies(page).results.map { it.toMovieModel() }
    }
  
  override suspend fun getUpcomingMovies(page: Int): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getUpcomingMovies(page).results.map { it.toMovieModel() }
    }
}