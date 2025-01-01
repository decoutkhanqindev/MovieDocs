package com.example.moviedocs.presentation.media

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.media.MediaListModel
import com.example.moviedocs.domain.usecase.media.GetMediaListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MediaListViewModel @Inject constructor(
  private val getMediaListUseCase: GetMediaListUseCase,
  private val saveStateHandle: SavedStateHandle,
) : ViewModel() {

  private val _uiState: MutableStateFlow<MediaUiState> = MutableStateFlow(MediaUiState.Loading)
  internal val uiState: StateFlow<MediaUiState> get() = _uiState.asStateFlow()

  private val movieId: Int = saveStateHandle["movieId"] ?: 0

  init {
    loadData(movieId)
  }

  fun loadData(movieId: Int) {
    viewModelScope.launch {
      _uiState.value = MediaUiState.Loading

      getMediaListUseCase(movieId)
        .onSuccess { it: MediaListModel ->
          _uiState.value = MediaUiState.Success(
            backdropList = it.backdrops,
            logoList = it.logos,
            posterList = it.posters
          )
        }
        .onFailure { it: Throwable ->
          _uiState.value = MediaUiState.Error(it)
          Timber.tag(this.javaClass.simpleName).e("loadData: ${it.message}")
        }
    }
  }

  fun setMovieId(movieId: Int) {
    saveStateHandle["movieId"] = movieId
  }
}