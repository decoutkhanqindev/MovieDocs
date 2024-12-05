package com.example.moviedocs.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.moviegenre.GenreListModel
import com.example.moviedocs.domain.model.movielist.MovieListModel
import com.example.moviedocs.domain.usecase.moviegenre.GetGenreListUseCase
import com.example.moviedocs.domain.usecase.movielist.GetNowPlayingUseCase
import com.example.moviedocs.domain.usecase.movielist.GetPopularUseCase
import com.example.moviedocs.domain.usecase.movielist.GetTopRatedUseCase
import com.example.moviedocs.domain.usecase.movielist.GetUpcomingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val getGenreListUseCase: GetGenreListUseCase,
  private val getNowPlayingUseCase: GetNowPlayingUseCase,
  private val getPopularUseCase: GetPopularUseCase,
  private val getUpcomingUseCase: GetUpcomingUseCase,
  private val getTopRatedUseCase: GetTopRatedUseCase,
) : ViewModel() {

  private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
  val uiState: StateFlow<HomeUiState> get() = _uiState.asStateFlow()

  init {
    getData()
  }

  private fun getData() {
    viewModelScope.launch {
      _uiState.value = HomeUiState.Loading

      // launch coroutines in parallel and wait for all of them to complete
      val genreListDeferred: Deferred<Result<GenreListModel>> =
        async { getGenreListUseCase() }
      val nowPlayingDeferred: Deferred<Result<MovieListModel>> =
        async { getNowPlayingUseCase(page = 1) }
      val popularDeferred: Deferred<Result<MovieListModel>> =
        async { getPopularUseCase(page = 1) }
      val upcomingDeferred: Deferred<Result<MovieListModel>> =
        async { getUpcomingUseCase(page = 1) }
      val topRatedDeferred: Deferred<Result<MovieListModel>> =
        async { getTopRatedUseCase(page = 1) }

      val genreListResult: Result<GenreListModel> = genreListDeferred.await()
      val nowPlayingResult: Result<MovieListModel> = nowPlayingDeferred.await()
      val popularResult: Result<MovieListModel> = popularDeferred.await()
      val upcomingResult: Result<MovieListModel> = upcomingDeferred.await()
      val topRatedResult: Result<MovieListModel> = topRatedDeferred.await()

      // check if all requests were successful
      if (genreListResult.isSuccess && nowPlayingResult.isSuccess && popularResult.isSuccess
        && upcomingResult.isSuccess && topRatedResult.isSuccess
      ) {
        _uiState.value = HomeUiState.Success(
          genreList = genreListResult.getOrNull()?.genres ?: emptyList(),
          nowPlaying = nowPlayingResult.getOrNull()?.results ?: emptyList(),
          popular = popularResult.getOrNull()?.results ?: emptyList(),
          upcoming = upcomingResult.getOrNull()?.results ?: emptyList(),
          topRated = topRatedResult.getOrNull()?.results ?: emptyList(),
        )
      }

      // if at least one request failed, emit the error
      else {
        val error = listOf(
          genreListResult, nowPlayingResult, popularResult, upcomingResult, topRatedResult
        ).first { it.isFailure }
        _uiState.value = HomeUiState.Error(error.exceptionOrNull() ?: Throwable())
        Timber.tag(this.javaClass.simpleName).e(error.exceptionOrNull())
      }
    }
  }

//  private fun updateUiState(update: (HomeUiState.Success) -> HomeUiState.Success) {
//    val currentState = _uiState.value
//    if (currentState is HomeUiState.Success) {
//      _uiState.value = update(currentState)
//    }
//  }
}