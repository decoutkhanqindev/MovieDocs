package com.example.moviedocs.presentation.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.externalId.ExternalIdsModel
import com.example.moviedocs.domain.model.country.CountryItemModel
import com.example.moviedocs.domain.model.credits.CreditListModel
import com.example.moviedocs.domain.model.language.LanguageItemModel
import com.example.moviedocs.domain.model.moviedetail.MovieDetailModel
import com.example.moviedocs.domain.usecase.country.GetCountryListUseCase
import com.example.moviedocs.domain.usecase.credits.GetCreditListUseCase
import com.example.moviedocs.domain.usecase.externalId.GetMovieDetailExternalIdsUseCase
import com.example.moviedocs.domain.usecase.language.GetLanguageListUseCase
import com.example.moviedocs.domain.usecase.moviedetail.GetMovieDetailUseCase
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
class MovieDetailViewModel @Inject constructor(
  private val getMovieDetailUseCase: GetMovieDetailUseCase,
  private val getExternalIdsUseCase: GetMovieDetailExternalIdsUseCase,
  private val getLanguageListUseCase: GetLanguageListUseCase,
  private val getCountryListUseCase: GetCountryListUseCase,
  private val getMovieCreditsUseCase: GetCreditListUseCase,
) : ViewModel() {

  private val _uiState: MutableStateFlow<MovieDetailUiState> =
    MutableStateFlow(MovieDetailUiState.Loading)
  val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

  fun loadData(movieId: Int) {
    viewModelScope.launch {
      _uiState.value = MovieDetailUiState.Loading

      val movieDetailDeferred: Deferred<Result<MovieDetailModel>> =
        async { getMovieDetailUseCase(movieId) }
      val externalIdsDeferred: Deferred<Result<ExternalIdsModel>> =
        async { getExternalIdsUseCase(movieId) }
      val languageListDeferred: Deferred<Result<List<LanguageItemModel>>> =
        async { getLanguageListUseCase() }
      val countryListDeferred: Deferred<Result<List<CountryItemModel>>> =
        async { getCountryListUseCase() }
      val creditListDeferred: Deferred<Result<CreditListModel>> =
        async { getMovieCreditsUseCase(movieId) }

      val movieDetailResult: Result<MovieDetailModel> = movieDetailDeferred.await()
      val externalIdsResult: Result<ExternalIdsModel> = externalIdsDeferred.await()
      val languageListResult: Result<List<LanguageItemModel>> = languageListDeferred.await()
      val countryListResult: Result<List<CountryItemModel>> = countryListDeferred.await()
      val creditListResult: Result<CreditListModel> = creditListDeferred.await()

      if (movieDetailResult.isSuccess && externalIdsResult.isSuccess
        && languageListResult.isSuccess && countryListResult.isSuccess
        && creditListResult.isSuccess
      ) {
        _uiState.value = MovieDetailUiState.Success(
          movieDetail = movieDetailResult.getOrNull()!!,
          externalIds = externalIdsResult.getOrNull()!!,
          languageList = languageListResult.getOrNull()!!,
          countryList = countryListResult.getOrNull()!!,
          castList = creditListResult.getOrNull()?.cast ?: emptyList(),
          crewList = creditListResult.getOrNull()?.crew ?: emptyList(),
        )
      } else {
        val error = listOf(
          movieDetailResult, externalIdsResult, languageListResult, countryListResult,
          creditListResult
        ).first { it.isFailure }
        _uiState.value = MovieDetailUiState.Error(error.exceptionOrNull() ?: Throwable())
        Timber.tag(this.javaClass.simpleName).e("loadData ${error.exceptionOrNull()}")
      }
    }
  }

//  private fun updateUiState(update: (MovieDetailUiState.Success) -> MovieDetailUiState.Success) {
//    val currentState: MovieDetailUiState = _uiState.value
//    if (currentState is MovieDetailUiState.Success) {
//      _uiState.value = update(currentState)
//    }
//  }
}