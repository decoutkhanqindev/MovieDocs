package com.example.moviedocs.presentation.moviedetail.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.FragmentMovieDetailMediaBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.media.MediaListHorizontalAdapter
import com.example.moviedocs.presentation.media.MediaType
import com.example.moviedocs.presentation.moviedetail.MovieDetailFragmentDirections
import com.example.moviedocs.presentation.moviedetail.MovieDetailUiState
import com.example.moviedocs.presentation.moviedetail.MovieDetailViewModel
import com.example.moviedocs.utils.download.Downloader
import com.example.moviedocs.utils.download.DownloaderImpl
import com.example.moviedocs.utils.formatTotalResult
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

  private val downloader: Downloader by lazy(LazyThreadSafetyMode.NONE) {
    DownloaderImpl(requireContext())
  }

  private val backdropAdapter: MediaListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MediaListHorizontalAdapter(MediaType.BACKDROP, downloader)
  }

  private val logoAdapter: MediaListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MediaListHorizontalAdapter(MediaType.LOGO, downloader)
  }

  private val posterAdapter: MediaListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MediaListHorizontalAdapter(MediaType.POSTER, downloader)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    movieId = arguments?.getInt("movieId")!!
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setUpNavigate()
    setUpRecyclerView(
      mRecyclerView = binding.backDropRecycleView,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.HORIZONTAL, false
      ),
      mAdapter = backdropAdapter,
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

  private fun setUpNavigate() {
    binding.apply {
      backDropMoreBtn.setOnClickListener {
        findNavController().navigate(
          MovieDetailFragmentDirections.actionMovieDetailFragmentToBackdropListFragment(movieId)
        )
      }

      logoMoreBtn.setOnClickListener {
        findNavController().navigate(
          MovieDetailFragmentDirections.actionMovieDetailFragmentToLogoListFragment(movieId)
        )
      }

      posterMoreBtn.setOnClickListener {
        findNavController().navigate(
          MovieDetailFragmentDirections.actionMovieDetailFragmentToPosterListFragment(movieId)
        )
      }
    }
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
        hideErrorDialog()

        binding.scrollView.invisible()
      }

      is MovieDetailUiState.Success -> {
        hideErrorDialog()

        binding.apply {
          scrollView.visible()
          backDropTotal.text = state.backDropList.size.formatTotalResult()
          logoTotal.text = state.logoList.size.formatTotalResult()
          posterTotal.text = state.posterList.size.formatTotalResult()
        }
        backdropAdapter.submitList(state.backDropList)
        logoAdapter.submitList(state.logoList)
        posterAdapter.submitList(state.posterList)
      }

      is MovieDetailUiState.Error -> {
        binding.scrollView.invisible()

        showErrorDialog { viewModel.loadData(movieId) }
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