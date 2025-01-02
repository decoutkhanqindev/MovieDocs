package com.example.moviedocs.presentation.credits

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.credits.CreditItemModel
import com.example.moviedocs.domain.model.credits.CreditListModel
import com.example.moviedocs.domain.usecase.credits.GetCreditListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreditListViewModel @Inject constructor(
  private val getCreditListUseCase: GetCreditListUseCase,
  private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private val _uiState: MutableStateFlow<CreditListUiState> =
    MutableStateFlow(CreditListUiState.Loading)
  val uiState: StateFlow<CreditListUiState> get() = _uiState.asStateFlow()

  private val movedId: Int = savedStateHandle.get<Int>("movieId") ?: 0

  init {
    loadCreditList(movedId)
  }

  fun loadCreditList(movieId: Int) {
    viewModelScope.launch {
      _uiState.value = CreditListUiState.Loading

      getCreditListUseCase(movieId).onSuccess { it: CreditListModel ->
        _uiState.value = CreditListUiState.Success(
          castList = it.cast,
          crewList = it.crew,
        )
      }.onFailure { it: Throwable ->
        _uiState.value = CreditListUiState.Error(it)
        Timber.tag("CreditListViewModel").e("loadCreditList $it")
      }
    }
  }

  fun sortCreditList(creditType: CreditType, sortType: CreditListUiState.SortType) {
    val currentState: CreditListUiState = _uiState.value
    if (currentState is CreditListUiState.Success) {
      val currentList: List<CreditItemModel> = when (creditType) {
        CreditType.CAST -> currentState.castList
        CreditType.CREW -> currentState.crewList
      }
      val sortedList: List<CreditItemModel> = when (sortType) {
        CreditListUiState.SortType.TITLE_ASC -> currentList.sortedBy { it.name }
        CreditListUiState.SortType.TITLE_DSC -> currentList.sortedByDescending { it.name }
        CreditListUiState.SortType.POPULARITY_ASC -> currentList.sortedBy { it.popularity }
        CreditListUiState.SortType.POPULARITY_DSC -> currentList.sortedByDescending { it.popularity }
      }
      _uiState.value = currentState.copy(castList = sortedList)
    }
  }

  fun setMovieId(movieId: Int) {
    savedStateHandle["movieId"] = movieId
  }
}