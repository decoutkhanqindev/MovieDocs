package com.example.moviedocs.presentation.credits

import com.example.moviedocs.domain.model.credits.CastItemModel
import com.example.moviedocs.domain.model.credits.CrewItemModel

sealed interface CreditListUiState {
  data object Loading : CreditListUiState

  data class Error(val throwable: Throwable) : CreditListUiState

  data class Success(
    val castList: List<CastItemModel>,
    val crewList: List<CrewItemModel>,
  ) : CreditListUiState

  enum class SortType {
    TITLE_ASC, TITLE_DSC, POPULARITY_ASC, POPULARITY_DSC
  }
}