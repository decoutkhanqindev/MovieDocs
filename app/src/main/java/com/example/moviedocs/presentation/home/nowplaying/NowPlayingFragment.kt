package com.example.moviedocs.presentation.home.nowplaying

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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
  
  private val viewModel: NowPlayingViewModel by viewModels()
  
  private val adapter: NowPlayingVerticalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    NowPlayingVerticalAdapter()
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    binding.movieListTitle.text = getString(R.string.now_playing)
    setUpNavigation()
    setUpRecyclerView()
    setUpScrollListener()
    bindViewModel()
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
      adapter = this@NowPlayingFragment.adapter
    }
  }
  
  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.movieListUiState.collect(::renderUi) })
  }
  
  private fun setUpScrollListener() {
    val layoutManager: LinearLayoutManager =
      binding.movieListRecyclerView.layoutManager as LinearLayoutManager
    binding.movieListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) { // scrolling down
          val visibleItemCount = layoutManager.childCount
          val totalItemCount = layoutManager.itemCount
          val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
          
          // Load more when there are fewer than 12 items remaining
          val threshold = 12 // 12 items (4 rows)
          if ((visibleItemCount + firstVisibleItemPosition + threshold) >= totalItemCount && firstVisibleItemPosition >= 0) {
            viewModel.loadNextPage()
          }
        }
      }
    })
  }
  
  private fun renderUi(state: MovieListUiState) {
    when (state) {
      MovieListUiState.FirstPageLoading -> {
        binding.apply {
          movieListProgressBar.visible()
          movieListBottomProgressBar.gone()
          movieListRecyclerView.gone()
        }
        adapter.submitList(emptyList())
      }
      
      MovieListUiState.FirstPageError -> {
        binding.apply {
          movieListProgressBar.visible()
          movieListBottomProgressBar.gone()
          movieListRecyclerView.gone()
        }
        adapter.submitList(emptyList())
      }
      
      is MovieListUiState.Success -> {
        binding.apply {
          movieListProgressBar.gone()
          movieListRecyclerView.visible()
          // show/hide load more progress bar
          when (state.nextPageState) {
            MovieListUiState.NextPageState.LOADING -> movieListBottomProgressBar.visible()
            else -> movieListBottomProgressBar.gone()
          }
          adapter.submitList(state.items)
        }
      }
    }
  }
  
  override fun onDestroyView() {
    binding.movieListRecyclerView.adapter = null
    super.onDestroyView()
  }
}