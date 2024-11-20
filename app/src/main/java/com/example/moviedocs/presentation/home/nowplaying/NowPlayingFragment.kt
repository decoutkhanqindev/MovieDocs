package com.example.moviedocs.presentation.home.nowplaying

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.R
import com.example.moviedocs.databinding.FragmentMovieListBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.home.MovieListUiState
import com.example.moviedocs.presentation.home.adapter.MovieListVerticalAdapter
import com.example.moviedocs.utils.gone
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.navigateBack
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowPlayingFragment :
  BaseFragment<FragmentMovieListBinding>(FragmentMovieListBinding::inflate) {
  
  private val viewModel: NowPlayingViewModel by viewModels()
  
  private val adapter: MovieListVerticalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListVerticalAdapter()
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    binding.movieListTitle.text = getString(R.string.now_playing)
    setUpNavigation()
    setUpRecyclerView()
    setUpScrollListener()
    bindViewModel()
    handleSortingMenuItemClick()
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
          // number of items currently visible on screen
          val visibleItemCount = layoutManager.childCount
          // total number of items in the list
          val totalItemCount = layoutManager.itemCount
          // position of the first visible item
          val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
          // trigger point before reaching the end
          val threshold = 2
          
          // If there are 2 more elements from the last element, you can call loadNextPage()
          // e.g:
          // [1][2][3][4][5]  <- Visible items (visibleItemCount = 5)
          // ↑ firstVisibleItemPosition = 1
          // Total items = 20
          // Threshold = 2
          // When: (5 + 1 + 2) >= 20 is true, load more
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
  
  private fun handleSortingMenuItemClick() {
    binding.toolBar.setOnMenuItemClickListener { it: MenuItem ->
      when (it.itemId) {
        R.id.titleAsc -> {
          viewModel.sortItems(SortType.TITLE_ASC)
          true
        }
        
        R.id.titleDsc -> {
          viewModel.sortItems(SortType.TITLE_DSC)
          true
        }
        
        R.id.ratingAsc -> {
          viewModel.sortItems(SortType.RATING_ASC)
          true
        }
        
        R.id.ratingDsc -> {
          viewModel.sortItems(SortType.RATING_DSC)
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