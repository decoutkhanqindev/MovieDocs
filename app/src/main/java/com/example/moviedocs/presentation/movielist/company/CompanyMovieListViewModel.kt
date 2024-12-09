package com.example.moviedocs.presentation.movielist.company

import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.movielist.MovieListModel
import com.example.moviedocs.domain.usecase.movielist.GetCompanyMovieListUseCase
import com.example.moviedocs.presentation.movielist.MovieListSingleEvent
import com.example.moviedocs.presentation.movielist.MovieListUiState
import com.example.moviedocs.presentation.movielist.MovieListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CompanyMovieListViewModel @Inject constructor(
  private val getCompanyMovieListUseCase: GetCompanyMovieListUseCase
) : MovieListViewModel() {

  fun loadPage(page: Int, companyId: Int) {
    viewModelScope.launch {
      _uiState.value = MovieListUiState.Loading

      getCompanyMovieListUseCase(page, companyId).onSuccess { it: MovieListModel ->
        _uiState.value = MovieListUiState.Success(
          items = it.results,
          currentPage = it.page,
          totalPage = it.totalPages,
          totalResults = it.totalResults
        )
        _singleEvent.send(MovieListSingleEvent.Success)
      }.onFailure { it: Throwable ->
        _uiState.value = MovieListUiState.Error(it)
        _singleEvent.send(MovieListSingleEvent.Error(it))
        Timber.tag(this.javaClass.simpleName).e("loadPage: ${it.message}")
      }
    }
  }
}