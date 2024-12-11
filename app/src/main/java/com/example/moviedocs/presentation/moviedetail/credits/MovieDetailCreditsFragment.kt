package com.example.moviedocs.presentation.moviedetail.credits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.FragmentMovieDetailCreditsBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.credits.cast.CastListHorizontalAdapter
import com.example.moviedocs.presentation.credits.crew.CrewListHorizontalAdapter
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

  private val viewModel: MovieDetailViewModel by activityViewModels()

  private val castListHorizontalAdapter: CastListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    CastListHorizontalAdapter()
  }

  private val crewListHorizontalAdapter: CrewListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    CrewListHorizontalAdapter()
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
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  private fun renderUi(state: MovieDetailUiState) {
    when (state) {
      MovieDetailUiState.Loading -> {
        binding.apply {
          castLayout.invisible()
          crewLayout.invisible()
        }
      }

      is MovieDetailUiState.Success -> {
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