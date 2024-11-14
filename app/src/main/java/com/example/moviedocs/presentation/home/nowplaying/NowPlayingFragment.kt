package com.example.moviedocs.presentation.home.nowplaying

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.R
import com.example.moviedocs.databinding.FragmentMovieListBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.home.MovieListUiState
import com.example.moviedocs.utils.gone
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.navigateBack
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowPlayingFragment :
  BaseFragment<FragmentMovieListBinding>(FragmentMovieListBinding::inflate) {
  
  private val viewModel: NowPlayingViewModel by activityViewModels()
  
  private val adapter: NowPlayingAdapter by lazy {
    NowPlayingAdapter()
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    binding.movieListTitle.text = getString(R.string.now_playing)
    setUpNavigation()
    setUpRecyclerView()
    bindViewModel()
  }
  
  private fun setUpNavigation() {
    binding.backBtn.navigateBack()
  }
  
  private fun setUpRecyclerView() {
    binding.movieListRecyclerView.apply {
      setHasFixedSize(true)
      layoutManager = GridLayoutManager(
        requireContext(), 2, LinearLayoutManager.VERTICAL, false
      )
      adapter = this@NowPlayingFragment.adapter
    }
  }
  
  private fun bindViewModel() {
    launchAndRepeatStarted(
      { viewModel.movieListUiState.collect(::renderUi) }
    )
  }
  
  private fun renderUi(state: MovieListUiState) {
    when (state) {
      MovieListUiState.FirstPageLoading -> {
        binding.apply {
          movieListProgressBar.visible()
          movieListRecyclerView.gone()
        }
        adapter.submitList(emptyList())
      }
      
      MovieListUiState.FirstPageError -> {
        binding.apply {
          movieListProgressBar.visible()
          movieListRecyclerView.gone()
        }
        adapter.submitList(emptyList())
      }
      
      is MovieListUiState.Success -> {
        binding.apply {
          movieListProgressBar.gone()
          movieListRecyclerView.visible()
        }
        adapter.submitList(state.items)
      }
    }
    
    // load next page
    val linearLayoutManager: LinearLayoutManager =
      binding.movieListRecyclerView.layoutManager as LinearLayoutManager
    
    binding.movieListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      // OnScrollListener designed to detect when the user scrolls near the bottom of the
      // RecyclerView and potentially trigger an action like loading more data
      // dx: The amount of horizontal scroll.
      // dy: The amount of vertical scroll.
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        // If there are 2 more elements from the last element, you can call loadNextPage()
        if ((dy > 0) && ((linearLayoutManager.findLastVisibleItemPosition() + 2) >= linearLayoutManager.itemCount)) {
          viewModel.loadNextPage()
        }
      }
    })
  }
}