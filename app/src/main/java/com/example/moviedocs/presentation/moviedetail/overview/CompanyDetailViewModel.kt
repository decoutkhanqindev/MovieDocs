package com.example.moviedocs.presentation.moviedetail.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedocs.domain.model.moviedetail.company.CompanyDetailModel
import com.example.moviedocs.domain.model.movielist.MovieListModel
import com.example.moviedocs.domain.usecase.moviedetail.GetCompanyDetailUseCase
import com.example.moviedocs.domain.usecase.moviedetail.GetCompanyMovieListUseCase
import com.example.moviedocs.domain.usecase.moviedetail.GetCountryListUseCase
import com.example.moviedocs.presentation.movielist.MovieListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
@HiltViewModel
class CompanyDetailViewModel @Inject constructor(
  private val getCompanyDetailUseCase: GetCompanyDetailUseCase,
  private val getCountryListUseCase: GetCountryListUseCase,
  private val getCompanyMovieListUseCase: GetCompanyMovieListUseCase,
) : ViewModel() {

  private var _companyDetailUiState: MutableStateFlow<CompanyDetailUiState> =
    MutableStateFlow(CompanyDetailUiState.Loading)
  private val companyDetailUiState: StateFlow<CompanyDetailUiState>
    get() = _companyDetailUiState.asStateFlow()

  private var _movieListUiState: MutableStateFlow<MovieListUiState> =
    MutableStateFlow(MovieListUiState.Loading)
  private val movieListUiState: StateFlow<MovieListUiState>
    get() = _movieListUiState.asStateFlow()

  val combinedUiState: StateFlow<Pair<CompanyDetailUiState, MovieListUiState>> =
    combine(
      companyDetailUiState, movieListUiState
    ) { companyState: CompanyDetailUiState, movieState: MovieListUiState ->
      companyState to movieState
    }.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = CompanyDetailUiState.Loading to MovieListUiState.Loading
    )

  fun getCompanyDetail(companyId: Int) {
    viewModelScope.launch {
      _companyDetailUiState.value = CompanyDetailUiState.Loading

      getCompanyDetailUseCase(companyId).onSuccess { it: CompanyDetailModel ->
        _companyDetailUiState.value = CompanyDetailUiState.Success(
          companyDetail = it,
          countryList = getCountryListUseCase().getOrThrow()
        )
      }.onFailure { it: Throwable ->
        _companyDetailUiState.value = CompanyDetailUiState.Error(it)
        Timber.tag("CompanyDetailViewModel").d("getCompanyDetail: ${it.message}")
      }
    }
  }

  fun getCompanyMoveList(page: Int, companyId: Int) {
    viewModelScope.launch {
      _movieListUiState.value = MovieListUiState.Loading

      getCompanyMovieListUseCase(page, companyId).onSuccess { it: MovieListModel ->
        _movieListUiState.value = MovieListUiState.Success(
          items = it.results,
          currentPage = it.page,
          totalPage = it.totalPages
        )
      }.onFailure { it: Throwable ->
        _movieListUiState.value = MovieListUiState.Error(it)
        Timber.tag("CompanyDetailViewModel").d("getCompanyMoveList: ${it.message}")
      }
    }
  }
}
