package com.example.moviedocs.presentation.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.externalId.ExternalIdsModel
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.domain.model.movielist.year.MovieListByYearModel
import com.example.moviedocs.domain.model.person.PersonDetailModel
import com.example.moviedocs.domain.model.person.PersonMovieCreditListModel
import com.example.moviedocs.domain.usecase.externalId.GetPersonDetailExternalIdsUseCase
import com.example.moviedocs.domain.usecase.person.GetPersonDetailUseCase
import com.example.moviedocs.domain.usecase.person.GetPersonMovieCreditListUseCase
import com.example.moviedocs.utils.formatDate
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
class PersonDetailViewModel @Inject constructor(
  private val getPersonDetailUseCase: GetPersonDetailUseCase,
  private val getPersonExternalIdsModel: GetPersonDetailExternalIdsUseCase,
  private val getPersonMovieCreditListUseCase: GetPersonMovieCreditListUseCase,
  private val saveStateHandle: SavedStateHandle,
) : ViewModel() {

  private val _uiState: MutableStateFlow<PersonDetailUiState> =
    MutableStateFlow(PersonDetailUiState.Loading)
  internal val uiState: StateFlow<PersonDetailUiState> = _uiState.asStateFlow()

  private var personId: Int = saveStateHandle.get<Int>("personId") ?: 0

  init {
    loadData(personId)
  }

  fun loadData(personId: Int) {
    viewModelScope.launch {
      _uiState.value = PersonDetailUiState.Loading

      val personDetailDeferred: Deferred<Result<PersonDetailModel>> =
        async { getPersonDetailUseCase(personId) }
      val personExternalIdsDeferred: Deferred<Result<ExternalIdsModel>> =
        async { getPersonExternalIdsModel(personId) }
      val personMovieCreditListDeferred: Deferred<Result<PersonMovieCreditListModel>> =
        async { getPersonMovieCreditListUseCase(personId) }

      val personDetailResult: Result<PersonDetailModel> = personDetailDeferred.await()
      val personExternalIdsResult: Result<ExternalIdsModel> = personExternalIdsDeferred.await()
      val personMovieCreditListResult: Result<PersonMovieCreditListModel> =
        personMovieCreditListDeferred.await()

      if (personDetailResult.isSuccess && personMovieCreditListResult.isSuccess) {
        _uiState.value = PersonDetailUiState.Success(
          personDetail = personDetailResult.getOrNull()!!,
          personExternalIds = personExternalIdsResult.getOrNull()!!,
          castMovieCreditList = personMovieCreditListResult.getOrNull()?.cast ?: emptyList(),
          crewMovieCreditList = personMovieCreditListResult.getOrNull()?.crew ?: emptyList(),
        )
      } else {
        val error = listOf(
          personDetailResult, personExternalIdsResult, personMovieCreditListResult,
        ).first { it.isFailure }
        _uiState.value = PersonDetailUiState.Error(error.exceptionOrNull() ?: Throwable())
        Timber.tag(this.javaClass.simpleName).e("loadData ${error.exceptionOrNull()}")
      }
    }
  }

  internal fun getMovieListByYear(movieCreditsList: List<MovieItemModel>): List<MovieListByYearModel> {
    val mapByYear: Map<String, List<MovieItemModel>> =
      movieCreditsList
        .groupBy { it: MovieItemModel ->
          if (it.releaseDate.formatDate() == "Unknown date") {
            "Unknown year"
          } else {
            it.releaseDate.formatDate().takeLast(4)
          }
        }
    val movieListByYear: List<MovieListByYearModel> =
      mapByYear
        .map { (year: String, movies: List<MovieItemModel>) ->
          MovieListByYearModel(year, movies.distinct())
        }
        .sortedByDescending { it.year }
    return movieListByYear
  }

  fun setPersonId(personId: Int) {
    saveStateHandle["personId"] = personId
  }
}