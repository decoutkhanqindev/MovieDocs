package com.example.moviedocs.presentation.media

import com.example.moviedocs.domain.model.media.MediaItemModel

sealed interface MediaUiState {
  data object Loading : MediaUiState
  data class Success(
    val backdropList: List<MediaItemModel>,
    val logoList: List<MediaItemModel>,
    val posterList: List<MediaItemModel>,
  ) : MediaUiState

  data class Error(val error: Throwable) : MediaUiState
}