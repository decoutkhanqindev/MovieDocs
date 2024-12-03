package com.example.moviedocs.presentation.moviedetail.overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.FragmentMovieDetailOverviewBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.moviedetail.MovieDetailUiState
import com.example.moviedocs.presentation.moviedetail.MovieDetailViewModel
import com.example.moviedocs.presentation.moviegenre.GenreListAdapter
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
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
  
  private val viewModel: MovieDetailViewModel by activityViewModels()
  
  private val genreListAdapter: GenreListAdapter by lazy(LazyThreadSafetyMode.NONE) {
    GenreListAdapter()
  }
  
  private val companyListAdapter: CompanyListAdapter by lazy(LazyThreadSafetyMode.NONE) {
    CompanyListAdapter()
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setUpRecyclerView(binding.genreRecycleView, genreListAdapter)
    setUpRecyclerView(binding.companyRecycleView, companyListAdapter)
    bindViewModel()
  }
  
  private fun setUpRecyclerView(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>
  ) {
     recyclerView.apply {
       setHasFixedSize(false)
       layoutManager = LinearLayoutManager(
         requireContext(), LinearLayoutManager.HORIZONTAL, false
       )
       this.adapter = adapter
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
          languageValue.text = state.movieDetail.originalLanguage.toLanguageName(state.languageList)
          countryValue.text = state.movieDetail.originCountry.joinToString(", ") {
            it.toCountryName(state.countryList)
          }
          budgetValue.text = "$${state.movieDetail.budget}"
          revenueValue.text = "$${state.movieDetail.revenue}"
          companyListAdapter.submitList(state.movieDetail.productionCompanies)
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
      companyRecycleView.adapter = null
    }
    super.onDestroyView()
  }
}