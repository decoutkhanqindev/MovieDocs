package com.example.moviedocs.presentation.movielist.nowplaying

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.R
import com.example.moviedocs.databinding.FragmentMovieListBinding
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.movielist.MovieListPageNumbersAdapter
import com.example.moviedocs.presentation.movielist.MovieListUiState
import com.example.moviedocs.presentation.movielist.MovieListVerticalAdapter
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.navigateBack
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowPlayingFragment : BaseFragment<FragmentMovieListBinding>(
  FragmentMovieListBinding::inflate
) {

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
    setUpRecyclerView(
      mRecyclerView = binding.movieListRecyclerView,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.VERTICAL, false
      ),
      mAdapter = movieListAdapter,
    )
    setUpRecyclerView(
      mRecyclerView = binding.movieListBottomPageNumbersRecyclerView,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.HORIZONTAL, false
      ),
      mAdapter = pageNumbersAdapter,
    )
    bindViewModel()
    handleLoadNextPage()
    handleSortingList()
  }

  private fun setUpNavigation() {
    binding.backBtn.navigateBack()

    movieListAdapter.setOnItemInAdapterClickListener { it: MovieItemModel ->
      findNavController().navigate(
        NowPlayingFragmentDirections.actionNowPlayingFragmentToMovieDetailFragment(
          movieId = it.id
        )
      )
    }
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  private fun renderUi(state: MovieListUiState) {
    when (state) {
      MovieListUiState.Loading -> {
        hideErrorDialog()

        binding.apply {
          movieListProgressBar.visible()
          movieListRecyclerView.invisible()
        }
      }

      is MovieListUiState.Success -> {
        hideErrorDialog()

        binding.apply {
          movieListProgressBar.invisible()
          movieListRecyclerView.visible()
        }
        pageNumbersAdapter.submitList((1..state.totalPage).toList())
        pageNumbersAdapter.setCurrentPage(state.currentPage - 1)
        movieListAdapter.submitList(state.items)
      }

      is MovieListUiState.Error -> {
        binding.apply {
          movieListProgressBar.visible()
          movieListRecyclerView.invisible()
        }

        showErrorDialog { viewModel.loadPage(1) }
      }
    }
  }

  private fun handleLoadNextPage() {
    pageNumbersAdapter.setOnItemInAdapterClickListener { it: Int ->
      viewModel.loadPage(it)
    }
  }

  private fun handleSortingList() {
    binding.toolBar.setOnMenuItemClickListener { it: MenuItem ->
      when (it.itemId) {
        R.id.movieTitleAsc -> {
          viewModel.sortList(MovieListUiState.SortType.TITLE_ASC)
          true
        }

        R.id.movieTitleDsc -> {
          viewModel.sortList(MovieListUiState.SortType.TITLE_DSC)
          true
        }

        R.id.movieRatingAsc -> {
          viewModel.sortList(MovieListUiState.SortType.RATING_ASC)
          true
        }

        R.id.movieRatingDsc -> {
          viewModel.sortList(MovieListUiState.SortType.RATING_DSC)
          true
        }

        R.id.moviepopularityAsc -> {
          viewModel.sortList(MovieListUiState.SortType.POPULARITY_ASC)
          true
        }

        R.id.moviepopularityAsc -> {
          viewModel.sortList(MovieListUiState.SortType.POPULARITY_DSC)
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