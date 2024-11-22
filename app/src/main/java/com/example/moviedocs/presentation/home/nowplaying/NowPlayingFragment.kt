package com.example.moviedocs.presentation.home.nowplaying

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.R
import com.example.moviedocs.databinding.FragmentMovieListBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.home.adapter.MovieListPageNumbersAdapter
import com.example.moviedocs.presentation.home.adapter.MovieListVerticalAdapter
import com.example.moviedocs.presentation.home.state.MovieListUiState
import com.example.moviedocs.utils.gone
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.navigateBack
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowPlayingFragment :
  BaseFragment<FragmentMovieListBinding>(FragmentMovieListBinding::inflate) {
  
  private val viewModel: NowPlayingViewModel by viewModels()
  
  private val movieListAdapter: MovieListVerticalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListVerticalAdapter()
  }
  
  private val pageNumbersAdapter: MovieListPageNumbersAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListPageNumbersAdapter()
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    binding.movieListTitle.text = getString(R.string.now_playing)
    setUpNavigation()
    setUpRecyclerView()
    bindViewModel()
    handleLoadNextPage()
    handleSortingMovies()
  }
  
  private fun setUpNavigation() {
    binding.backBtn.navigateBack()
  }
  
  private fun setUpRecyclerView() {
    binding.movieListRecyclerView.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.VERTICAL, false
      )
      adapter = this@NowPlayingFragment.movieListAdapter
    }
    
    binding.movieListBottomPageNumbersRecyclerView.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.HORIZONTAL, false
      )
      adapter = this@NowPlayingFragment.pageNumbersAdapter
    }
  }
  
  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.movieListUiState.collect(::renderUi) })
  }
  
  private fun renderUi(state: MovieListUiState) {
    when (state) {
      MovieListUiState.Loading -> {
        binding.apply {
          movieListProgressBar.visible()
          movieListRecyclerView.gone()
        }
        movieListAdapter.submitList(emptyList())
      }
      
      is MovieListUiState.Error -> {
        binding.apply {
          movieListProgressBar.visible()
          movieListRecyclerView.gone()
        }
        movieListAdapter.submitList(emptyList())
      }
      
      is MovieListUiState.Success -> {
        binding.apply {
          movieListProgressBar.gone()
          movieListRecyclerView.visible()
        }
        pageNumbersAdapter.submitList((1..state.totalPage).toList())
        pageNumbersAdapter.setCurrentPage(state.currentPage - 1)
        movieListAdapter.submitList(state.items)
      }
    }
  }
  
  private fun handleLoadNextPage() {
    pageNumbersAdapter.setOnItemClickListener { it: Int ->
      viewModel.loadPage(it)
    }
  }
  
  private fun handleSortingMovies() {
    binding.toolBar.setOnMenuItemClickListener { it: MenuItem ->
      when (it.itemId) {
        R.id.titleAsc -> {
          viewModel.sortItems(MovieListUiState.SortType.TITLE_ASC)
          true
        }
        
        R.id.titleDsc -> {
          viewModel.sortItems(MovieListUiState.SortType.TITLE_DSC)
          true
        }
        
        R.id.ratingAsc -> {
          viewModel.sortItems(MovieListUiState.SortType.RATING_ASC)
          true
        }
        
        R.id.ratingDsc -> {
          viewModel.sortItems(MovieListUiState.SortType.RATING_DSC)
          true
        }
        
        else -> false
      }
    }
  }
  
  override fun onDestroyView() {
    binding.movieListRecyclerView.adapter = null
    super.onDestroyView()
  }
}