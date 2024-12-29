package com.example.moviedocs.presentation.moviedetail.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.FragmentMovieDetailMediaBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.media.MediaListAdapter
import com.example.moviedocs.presentation.moviedetail.MovieDetailUiState
import com.example.moviedocs.presentation.moviedetail.MovieDetailViewModel
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailMediaFragment : BaseFragment<FragmentMovieDetailMediaBinding>(
  FragmentMovieDetailMediaBinding::inflate
) {

  companion object {
    fun newInstance(): MovieDetailMediaFragment = MovieDetailMediaFragment()
  }

  private var movieId: Int = 0

  private val viewModel: MovieDetailViewModel by viewModels()

  private val backDropAdapter: MediaListAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MediaListAdapter(MediaListAdapter.MediaType.BACKDROPS)
  }

  private val logoAdapter: MediaListAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MediaListAdapter(MediaListAdapter.MediaType.LOGOS)
  }

  private val posterAdapter: MediaListAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MediaListAdapter(MediaListAdapter.MediaType.POSTERS)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    movieId = arguments?.getInt("movieId")!!
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setUpRecyclerView(
      mRecyclerView = binding.backDropRecycleView,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.HORIZONTAL, false
      ),
      mAdapter = backDropAdapter,
    )
    setUpRecyclerView(
      mRecyclerView = binding.logoRecycleView,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.HORIZONTAL, false
      ),
      mAdapter = logoAdapter,
    )
    setUpRecyclerView(
      mRecyclerView = binding.posterRecycleView,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.HORIZONTAL, false
      ),
      mAdapter = posterAdapter,
    )
    viewModel.setMovieId(movieId)
    bindViewModel()
  }

  private fun bindViewModel() {
    viewLifecycleOwner.lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.uiState.collect(::renderUi)
      }
    }
  }

  private fun renderUi(state: MovieDetailUiState) {
    when (state) {
      MovieDetailUiState.Loading -> {
        binding.scrollView.invisible()
      }

      is MovieDetailUiState.Success -> {
        binding.scrollView.visible()

        backDropAdapter.submitList(state.backDropList)
        logoAdapter.submitList(state.logoList)
        posterAdapter.submitList(state.posterList)
      }

      is MovieDetailUiState.Error -> {
        binding.scrollView.invisible()
      }
    }
  }

  override fun onDestroyView() {
    binding.apply {
      backDropRecycleView.adapter = null
      logoRecycleView.adapter = null
      posterRecycleView.adapter = null
    }
    super.onDestroyView()
  }
}