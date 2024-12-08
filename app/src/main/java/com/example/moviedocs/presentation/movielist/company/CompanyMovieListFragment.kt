package com.example.moviedocs.presentation.movielist.company

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
class CompanyMovieListFragment : BaseFragment<FragmentMovieListBinding>(
  FragmentMovieListBinding::inflate
) {

  private val args: CompanyMovieListFragmentArgs by navArgs()

  private val viewModel: CompanyMovieListViewModel by viewModels()

  private val movieListAdapter: MovieListVerticalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListVerticalAdapter()
  }

  private val pageNumbersAdapter: MovieListPageNumbersAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListPageNumbersAdapter()
  }

  @SuppressLint("SetTextI18n")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.movieListTitle.text = "By ${args.companyName}"
    viewModel.loadPage(1, args.companyId)
    setUpNavigation()
    setUpRecyclerView(
      binding.movieListRecyclerView,
      LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
      movieListAdapter,
    )
    setUpRecyclerView(
      binding.movieListBottomPageNumbersRecyclerView,
      LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
      pageNumbersAdapter,
    )
    bindViewModel()
    handleLoadNextPage()
    handleSortingMovies()
  }

  private fun setUpNavigation() {
    binding.backBtn.navigateBack()

    movieListAdapter.onItemClickListener = { it: MovieItemModel ->
      findNavController().navigate(
        CompanyMovieListFragmentDirections.Companion.actionCompanyMovieListFragmentToMovieDetailFragment(
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
        binding.apply {
          movieListProgressBar.visible()
          movieListRecyclerView.invisible()
        }
        movieListAdapter.submitList(emptyList())
      }

      is MovieListUiState.Error -> {
        binding.apply {
          movieListProgressBar.visible()
          movieListRecyclerView.invisible()
        }
        movieListAdapter.submitList(emptyList())
      }

      is MovieListUiState.Success -> {
        binding.apply {
          movieListProgressBar.invisible()
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
      viewModel.loadPage(it, args.companyId)
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