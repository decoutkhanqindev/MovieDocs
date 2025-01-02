package com.example.moviedocs.presentation.moviedetail.credits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.FragmentMovieDetailCreditsBinding
import com.example.moviedocs.domain.model.credits.CreditItemModel
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.credits.CreditListHorizontalAdapter
import com.example.moviedocs.presentation.moviedetail.MovieDetailFragmentDirections
import com.example.moviedocs.presentation.moviedetail.MovieDetailUiState
import com.example.moviedocs.presentation.moviedetail.MovieDetailViewModel
import com.example.moviedocs.utils.formatTotalResult
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailCreditsFragment : BaseFragment<FragmentMovieDetailCreditsBinding>(
  FragmentMovieDetailCreditsBinding::inflate
) {
  companion object {
    fun newInstance(): MovieDetailCreditsFragment = MovieDetailCreditsFragment()
  }

  private var movieId: Int = 0

  private val viewModel: MovieDetailViewModel by viewModels()

  private val castListHorizontalAdapter: CreditListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    CreditListHorizontalAdapter()
  }

  private val crewListHorizontalAdapter: CreditListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    CreditListHorizontalAdapter()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    movieId = arguments?.getInt("movieId")!!
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setUpNavigation()
    setUpRecyclerView(
      mRecyclerView = binding.castRecycleView,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.HORIZONTAL, false
      ),
      mAdapter = castListHorizontalAdapter,
    )
    setUpRecyclerView(
      mRecyclerView = binding.crewRecycleView,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.HORIZONTAL, false
      ),
      mAdapter = crewListHorizontalAdapter,
    )
    viewModel.setMovieId(movieId)
    bindViewModel()
  }

  private fun setUpNavigation() {
    binding.apply {
      castMoreBtn.setOnClickListener {
        findNavController().navigate(
          MovieDetailFragmentDirections.actionMovieDetailFragmentToCastListFragment(
            movieId = movieId
          )
        )
      }

      crewMoreBtn.setOnClickListener {
        findNavController().navigate(
          MovieDetailFragmentDirections.actionMovieDetailFragmentToCrewListFragment(
            movieId = movieId
          )
        )
      }
    }

    castListHorizontalAdapter.setOnItemClickListener { it: CreditItemModel ->
      findNavController().navigate(
        MovieDetailFragmentDirections.actionMovieDetailFragmentToPersonDetailFragment(
          personId = it.id
        )
      )
    }

    crewListHorizontalAdapter.setOnItemClickListener { it: CreditItemModel ->
      findNavController().navigate(
        MovieDetailFragmentDirections.actionMovieDetailFragmentToPersonDetailFragment(
          personId = it.id
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

        binding.apply {
          castLayout.invisible()
          crewLayout.invisible()
        }
      }

      is MovieDetailUiState.Success -> {
        hideErrorDialog()

        binding.apply {
          castLayout.visible()
          crewLayout.visible()
          totalCasts.text = state.castList.size.formatTotalResult()
          totalCrews.text = state.crewList.size.formatTotalResult()
        }
        castListHorizontalAdapter.submitList(state.castList)
        crewListHorizontalAdapter.submitList(state.crewList)
      }

      is MovieDetailUiState.Error -> {
        binding.apply {
          castLayout.invisible()
          crewLayout.invisible()
        }

        showErrorDialog { viewModel.loadData(movieId) }
      }
    }
  }

  override fun onDestroyView() {
    binding.apply {
      castRecycleView.adapter = null
      crewRecycleView.adapter = null
    }
    super.onDestroyView()
  }
}