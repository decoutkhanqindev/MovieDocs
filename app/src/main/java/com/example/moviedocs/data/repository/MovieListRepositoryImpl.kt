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
  
  override suspend fun getNowPlaying(page: Int): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getNowPlaying(page).results.map { it.toMovieModel() }
    }
  
  override suspend fun getPopular(page: Int): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getPopular(page).results.map { it.toMovieModel() }
    }
  
  override suspend fun getUpcoming(page: Int): Result<List<MovieModel>> =
    runSuspendCatching(ioDispatcher) {
      apiService.getUpcoming(page).results.map { it.toMovieModel() }
    }
  
  override suspend fun getTopRated(page: Int): Result<List<MovieModel>> =
    runSuspendCatching {
      apiService.getTopRated(page).results.map { it.toMovieModel() }
    }
}