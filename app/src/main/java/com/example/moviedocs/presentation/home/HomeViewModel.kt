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
    getGenreList()
    getNowPlaying()
    getPopular()
    getUpcoming()
    getTopRated()
  }
  
  private fun getGenreList() {
    viewModelScope.launch {
      getGenreListUseCase().onSuccess { genreListModel: GenreListModel ->
        updateUiState { currentState: HomeUiState.Success ->
          currentState.copy(genreList = genreListModel.genres)
        }
      }.onFailure { throwable: Throwable ->
        _uiState.value = HomeUiState.Error(throwable)
        Timber.tag(this.javaClass.simpleName).e("getGenreList: ${throwable.message}")
      }
    }
  }
  
  private fun getNowPlaying() {
    viewModelScope.launch {
      getNowPlayingUseCase(page = 1).onSuccess { movieList: MovieListModel ->
        updateUiState { currentState: HomeUiState.Success ->
          currentState.copy(nowPlaying = movieList.results)
        }
      }.onFailure { throwable: Throwable ->
        _uiState.value = HomeUiState.Error(throwable)
        Timber.tag(this.javaClass.simpleName).e("getNowPlaying: ${throwable.message}")
      }
    }
  }
  
  private fun getPopular() {
    viewModelScope.launch {
      getPopularUseCase(page = 1).onSuccess { movieList: MovieListModel ->
        updateUiState { currentState: HomeUiState.Success ->
          currentState.copy(popular = movieList.results)
        }
      }.onFailure { throwable: Throwable ->
        _uiState.value = HomeUiState.Error(throwable)
        Timber.tag(this.javaClass.simpleName).e("getPopular: ${throwable.message}")
      }
    }
  }
  
  private fun getUpcoming() {
    viewModelScope.launch {
      getUpcomingUseCase(page = 1).onSuccess { movieList: MovieListModel ->
        updateUiState { currentState: HomeUiState.Success ->
          currentState.copy(upcoming = movieList.results)
        }
      }.onFailure { throwable: Throwable ->
        _uiState.value = HomeUiState.Error(throwable)
        Timber.tag(this.javaClass.simpleName).e("getUpcoming: ${throwable.message}")
      }
    }
  }
  
  private fun getTopRated() {
    viewModelScope.launch {
      getTopRatedUseCase(page = 1).onSuccess { movieList: MovieListModel ->
        updateUiState { currentState: HomeUiState.Success ->
          currentState.copy(topRated = movieList.results)
        }
      }.onFailure { throwable: Throwable ->
        _uiState.value = HomeUiState.Error(throwable)
        Timber.tag(this.javaClass.simpleName).e("getTopRated: ${throwable.message}")
      }
    }
  }
  
  private fun updateUiState(update: (HomeUiState.Success) -> HomeUiState.Success) {
    val currentState = _uiState.value
    if (currentState is HomeUiState.Success) {
      _uiState.value = update(currentState)
    } else {
      _uiState.value = update(HomeUiState.Success())
    }
  }
}