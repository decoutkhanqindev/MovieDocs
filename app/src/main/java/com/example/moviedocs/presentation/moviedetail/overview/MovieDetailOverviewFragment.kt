package com.example.moviedocs.presentation.moviedetail.overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.FragmentMovieDetailOverviewBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.moviedetail.MovieDetailUiState
import com.example.moviedocs.presentation.moviedetail.MovieDetailViewModel
import com.example.moviedocs.presentation.moviegenre.GenreListAdapter
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailOverviewFragment : BaseFragment<FragmentMovieDetailOverviewBinding>(
  FragmentMovieDetailOverviewBinding::inflate
) {
  
  companion object {
    fun newInstance(): MovieDetailOverviewFragment = MovieDetailOverviewFragment()
  }
  
  private val viewModel: MovieDetailViewModel by activityViewModels()
  
  private val genreListAdapter: GenreListAdapter by lazy(LazyThreadSafetyMode.NONE) {
    GenreListAdapter()
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setUpRecyclerView()
    bindViewModel()
  }
  
  private fun setUpRecyclerView() {
    binding.genreRecycleView.apply {
      setHasFixedSize(false)
      layoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.HORIZONTAL, false
      )
      adapter = genreListAdapter
    }
  }
  
  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }
  
  @SuppressLint("SetTextI18n")
  private fun renderUi(state: MovieDetailUiState) {
    when (state) {
      MovieDetailUiState.Loading -> {
        binding.apply {
          movieDetailOverview.invisible()
          movieDetailGenreLayout.invisible()
          movieDetailStatusLayout.invisible()
          movieDetailLanguageLayout.invisible()
          movieDetailCountryLayout.invisible()
        }
      }
      
      is MovieDetailUiState.Success -> {
        binding.apply {
          movieDetailOverview.visible()
          movieDetailGenreLayout.visible()
          movieDetailStatusLayout.visible()
          movieDetailLanguageLayout.visible()
          movieDetailCountryLayout.visible()
          
          movieDetailOverview.text = state.movieDetail.overview
          genreListAdapter.submitList(state.movieDetail.genres)
          statusValue.text = state.movieDetail.status
          languageValue.text = state.movieDetail.originalLanguage
          countryValue.text = state.movieDetail.originCountry.joinToString { "," }
          budgetValue.text = "$${state.movieDetail.budget}"
          revenueValue.text = "$${state.movieDetail.revenue}"
        }
      }
      
      is MovieDetailUiState.Error -> {
        binding.apply {
          movieDetailOverview.invisible()
          movieDetailGenreLayout.invisible()
          movieDetailStatusLayout.invisible()
          movieDetailLanguageLayout.invisible()
          movieDetailCountryLayout.invisible()
        }
      }
    }
  }
  
  override fun onDestroyView() {
    binding.apply {
      genreRecycleView.adapter = null
    }
    super.onDestroyView()
  }
}