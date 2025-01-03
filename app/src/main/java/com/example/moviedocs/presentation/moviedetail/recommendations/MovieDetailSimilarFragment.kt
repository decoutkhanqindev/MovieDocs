package com.example.moviedocs.presentation.moviedetail.recommendations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.FragmentMovieDetailSimilarBinding
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.moviedetail.MovieDetailFragmentDirections
import com.example.moviedocs.presentation.moviedetail.MovieDetailUiState
import com.example.moviedocs.presentation.moviedetail.MovieDetailViewModel
import com.example.moviedocs.presentation.movielist.MovieListHorizontalAdapter
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailSimilarFragment : BaseFragment<FragmentMovieDetailSimilarBinding>(
  FragmentMovieDetailSimilarBinding::inflate
) {

  companion object {
    fun newInstance(): MovieDetailSimilarFragment = MovieDetailSimilarFragment()
  }

  private val viewModel: MovieDetailViewModel by viewModels()

  private val adapter: MovieListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListHorizontalAdapter()
  }

  private var movieId: Int = 0

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    movieId = arguments?.getInt("movieId") ?: 0
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setUpNavigation()
    setUpRecyclerView(
      mRecyclerView = binding.similarMovieListRecyclerview,
      mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
      mAdapter = adapter
    )
    viewModel.setMovieId(movieId)
    bindViewModel()
  }

  private fun setUpNavigation() {
    binding.moreBtn.setOnClickListener {

    }

    adapter.setOnItemInAdapterClickListener { it: MovieItemModel ->
      findNavController().navigate(
        MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieDetailFragment(
          movieId = it.id
        )
      )
    }
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  private fun renderUi(state: MovieDetailUiState) {
    when (state) {
      MovieDetailUiState.Loading -> {
        hideErrorDialog()

        binding.similarMovieListRecyclerview.invisible()
      }

      is MovieDetailUiState.Success -> {
        hideErrorDialog()

        binding.similarMovieListRecyclerview.visible()
        adapter.submitList(state.similarMovieList)
      }

      is MovieDetailUiState.Error -> {
        binding.similarMovieListRecyclerview.invisible()

        showErrorDialog { viewModel.loadData(movieId) }
      }
    }
  }

  override fun onDestroyView() {
    binding.similarMovieListRecyclerview.adapter = null
    super.onDestroyView()
  }
}