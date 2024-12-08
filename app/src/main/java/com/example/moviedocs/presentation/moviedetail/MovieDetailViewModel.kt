package com.example.moviedocs.presentation.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel
import com.example.moviedocs.domain.usecase.country.GetCountryListUseCase
import com.example.moviedocs.domain.usecase.language.GetLanguageListUseCase
import com.example.moviedocs.domain.usecase.moviedetail.GetExternalIdsUseCase
import com.example.moviedocs.domain.usecase.moviedetail.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
  private val getMovieDetailUseCase: GetMovieDetailUseCase,
  private val getExternalIdsUseCase: GetExternalIdsUseCase,
  private val getLanguageListUseCase: GetLanguageListUseCase,
  private val getCountryListUseCase: GetCountryListUseCase,
) : ViewModel() {
  
  private val _uiState: MutableStateFlow<MovieDetailUiState> =
    MutableStateFlow(MovieDetailUiState.Loading)
  val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()
  
  fun loadMovieDetail(movieId: Int) {
    viewModelScope.launch {
      _uiState.value = MovieDetailUiState.Loading
      
      getMovieDetailUseCase(movieId = movieId).onSuccess { it: MovieDetailModel ->
        _uiState.value = MovieDetailUiState.Success(
          movieDetail = it,
          externalIds = getExternalIdsUseCase(movieId = movieId).getOrThrow(),
          languageList = getLanguageListUseCase().getOrThrow(),
          countryList = getCountryListUseCase().getOrThrow(),
        )
      }.onFailure { it: Throwable ->
        _uiState.value = MovieDetailUiState.Error(throwable = it)
        Timber.tag(this.javaClass.simpleName).e("getMovieDetail: $it")
      }
    }
  }
}