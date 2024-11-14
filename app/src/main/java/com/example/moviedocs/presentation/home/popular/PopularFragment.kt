package com.example.moviedocs.presentation.home.popular

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
class PopularFragment :
  BaseFragment<FragmentMovieListBinding>(FragmentMovieListBinding::inflate) {
  
  private val viewModel: PopularViewModel by activityViewModels()
  
  private val adapter: PopularAdapter by lazy(LazyThreadSafetyMode.NONE) {
    PopularAdapter()
  }
  
  private var isLoadingMore: Boolean = false
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    binding.movieListTitle.text = getString(R.string.popular)
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
      layoutManager = GridLayoutManager(
        requireContext(), 3, LinearLayoutManager.VERTICAL, false
      )
      adapter = this@PopularFragment.adapter
    }
  }
  
  private fun bindViewModel() {
    launchAndRepeatStarted(
      { viewModel.movieListUiState.collect(::renderUi) }
    )
  }
  
  private fun setUpScrollListener() {
    val layoutManager = binding.movieListRecyclerView.layoutManager as GridLayoutManager
    binding.movieListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) { // scrolling down
          val visibleItemCount = layoutManager.childCount
          val totalItemCount = layoutManager.itemCount
          val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
          
          // load more when user is near the end
          if ((visibleItemCount + firstVisibleItemPosition)
            >= totalItemCount - 3 && firstVisibleItemPosition >= 0
          ) {
            val currentState = viewModel.movieListUiState.value
            if (currentState is MovieListUiState.Success
              && currentState.nextPageState == MovieListUiState.NextPageState.LOAD_MORE
            ) {
              isLoadingMore = true
              viewModel.loadNextPage()
            }
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
            MovieListUiState.NextPageState.LOADING -> {
              movieListBottomProgressBar.visible()
              isLoadingMore = true
            }
            
            MovieListUiState.NextPageState.DONE -> {
              movieListProgressBar.gone()
              isLoadingMore = false
            }
            
            MovieListUiState.NextPageState.ERROR -> {
              movieListBottomProgressBar.gone()
              isLoadingMore = false
            }
            
            MovieListUiState.NextPageState.LOAD_MORE -> {
              movieListBottomProgressBar.gone()
              isLoadingMore = false
            }
          }
        }
        adapter.submitList(state.items)
      }
    }
  }
}