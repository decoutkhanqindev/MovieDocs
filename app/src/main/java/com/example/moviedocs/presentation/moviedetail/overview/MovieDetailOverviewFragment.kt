package com.example.moviedocs.presentation.moviedetail.overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.moviedocs.databinding.FragmentMovieDetailOverviewBinding
import com.example.moviedocs.domain.model.genre.GenreItemModel
import com.example.moviedocs.domain.model.moviedetail.company.CompanyItemModel
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.company.CompanyListHorizontalAdapter
import com.example.moviedocs.presentation.genre.GenreListAdapter
import com.example.moviedocs.presentation.moviedetail.MovieDetailFragmentDirections
import com.example.moviedocs.presentation.moviedetail.MovieDetailUiState
import com.example.moviedocs.presentation.moviedetail.MovieDetailViewModel
import com.example.moviedocs.utils.formatDollar
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.toCountryName
import com.example.moviedocs.utils.toLanguageName
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailOverviewFragment : BaseFragment<FragmentMovieDetailOverviewBinding>(
  FragmentMovieDetailOverviewBinding::inflate
) {
  companion object {
    fun newInstance(): MovieDetailOverviewFragment = MovieDetailOverviewFragment()
  }

  private var movieId: Int = 0

  private val viewModel: MovieDetailViewModel by viewModels()

  private val genreListAdapter: GenreListAdapter by lazy(LazyThreadSafetyMode.NONE) {
    GenreListAdapter()
  }

  private val companyListAdapter: CompanyListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    CompanyListHorizontalAdapter()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    movieId = arguments?.getInt("movieId")!!
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setUpNavigation()
    setUpRecyclerView(
      mRecyclerView = binding.genreRecycleView,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.HORIZONTAL, false
      ),
      mAdapter = genreListAdapter,
    )
    setUpRecyclerView(
      mRecyclerView = binding.companyRecycleView,
      mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL),
      mAdapter = companyListAdapter,
    )
    viewModel.setMovieId(movieId)
    bindViewModel()
  }

  private fun setUpNavigation() {
    genreListAdapter.setOnItemClickListener { it: GenreItemModel ->
      findNavController().navigate(
        MovieDetailFragmentDirections.actionMovieDetailFragmentToGenreMovieListFragment(
          genreId = it.id, genreName = it.name
        )
      )
    }

    companyListAdapter.setOnItemClickListener { it: CompanyItemModel ->
      findNavController().navigate(
        MovieDetailFragmentDirections.actionMovieDetailFragmentToCompanyDetailFragment(
          companyId = it.id
        )
      )
    }
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  @SuppressLint("SetTextI18n")
  private fun renderUi(state: MovieDetailUiState) {
    when (state) {
      MovieDetailUiState.Loading -> {
        binding.scrollView.invisible()
      }

      is MovieDetailUiState.Success -> {
        binding.apply {
          scrollView.visible()

          movieDetailOverview.text = state.movieDetail.overview
          genreListAdapter.submitList(state.movieDetail.genres)
          statusValue.text = state.movieDetail.status
          languageValue.text = state.movieDetail.originalLanguage.toLanguageName(state.languageList)
          countryValue.text = state.movieDetail.originCountry.joinToString(", ") {
            it.toCountryName(state.countryList)
          }
          budgetValue.text = state.movieDetail.budget.formatDollar()
          revenueValue.text = state.movieDetail.revenue.formatDollar()
          companyListAdapter.submitList(state.movieDetail.productionCompanies)
        }
      }

      is MovieDetailUiState.Error -> {
        binding.scrollView.invisible()
      }
    }
  }

  override fun onDestroyView() {
    binding.apply {
      genreRecycleView.adapter = null
      companyRecycleView.adapter = null
    }
    super.onDestroyView()
  }
}