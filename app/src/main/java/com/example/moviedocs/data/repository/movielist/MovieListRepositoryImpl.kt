package com.example.moviedocs.data.repository.movielist

import com.example.moviedocs.data.mapper.movielist.toMovieListModel
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.movielist.MovieListModel
import com.example.moviedocs.domain.repository.movielist.MovieListRepository
import com.example.moviedocs.utils.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
  private val apiService: ApiService,
  @AppDispatcher(DispatcherType.IO)
  private val ioDispatcher: CoroutineDispatcher,
) : MovieListRepository {

  override suspend fun getNowPlaying(page: Int): Result<MovieListModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getNowPlaying(page).toMovieListModel()
    }

  override suspend fun getPopular(page: Int): Result<MovieListModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getPopular(page).toMovieListModel()
    }

  override suspend fun getUpcoming(page: Int): Result<MovieListModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getUpcoming(page).toMovieListModel()
    }

  override suspend fun getTopRated(page: Int): Result<MovieListModel> =
    runSuspendCatching {
      apiService.getTopRated(page).toMovieListModel()
    }

  override suspend fun getCompanyMovieList(page: Int, companyId: Int): Result<MovieListModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getCompanyMovieList(page = page, companyId = companyId).toMovieListModel()
    }

  override suspend fun getGenreMovieList(page: Int, genreId: Int): Result<MovieListModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getGenreMovieList(page, genreId).toMovieListModel()
    }

  override suspend fun getSimilarMovieList(movieId: Int, page: Int): Result<MovieListModel> =
    runSuspendCatching(ioDispatcher) {
      apiService.getSimilarMovieList(movieId, page).toMovieListModel()
    }
}