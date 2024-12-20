package com.example.moviedocs.presentation.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.genre.GenreListModel
import com.example.moviedocs.domain.usecase.genre.GetGenreListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GenreListViewModel @Inject constructor(
  private val getGenreListUseCase: GetGenreListUseCase,
) : ViewModel() {

  private val _uiState: MutableStateFlow<GenreListUiState> =
    MutableStateFlow(GenreListUiState.Loading)
  internal val uiState: StateFlow<GenreListUiState> get() = _uiState.asStateFlow()

  init {
    loadGenreList()
  }

  private fun loadGenreList() {
    viewModelScope.launch {
      _uiState.value = GenreListUiState.Loading

      getGenreListUseCase()
        .onSuccess { it: GenreListModel ->
          _uiState.value = GenreListUiState.Success(items = it.genres)
        }
        .onFailure { it: Throwable ->
          _uiState.value = GenreListUiState.Error(it)
          Timber.tag(this.javaClass.simpleName).e("loadGenreList: $it")
        }
    }
  }
}