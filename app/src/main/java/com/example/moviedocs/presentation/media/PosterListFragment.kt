package com.example.moviedocs.presentation.media

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.moviedocs.databinding.FragmentMediaListBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.utils.download.Downloader
import com.example.moviedocs.utils.download.DownloaderImpl
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.navigateBack
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PosterListFragment : BaseFragment<FragmentMediaListBinding>(
  FragmentMediaListBinding::inflate
) {

  private val args: PosterListFragmentArgs by navArgs()

  private val viewModel: MediaListViewModel by viewModels()

  private val downloader: Downloader by lazy(LazyThreadSafetyMode.NONE) {
    DownloaderImpl(requireContext())
  }

  private val adapter: MediaListVerticalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MediaListVerticalAdapter(MediaType.POSTER, downloader)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.mediaListTitle.text = "Poster List"
    setUpRecyclerView(
      mRecyclerView = binding.mediaListRecyclerview,
      mLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL),
      mAdapter = adapter
    )
    setUpNavigate()
    viewModel.setMovieId(args.movieId)
    bindViewModel()
  }

  private fun setUpNavigate() {
    binding.backBtn.navigateBack()
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  private fun renderUi(state: MediaUiState) {
    when (state) {
      MediaUiState.Loading -> {
        hideErrorDialog()

        binding.mediaListRecyclerview.invisible()
      }

      is MediaUiState.Success -> {
        hideErrorDialog()

        binding.mediaListRecyclerview.visible()

        adapter.submitList(state.posterList)
      }

      is MediaUiState.Error -> {
        binding.mediaListRecyclerview.invisible()

        showErrorDialog { viewModel.loadData(args.movieId) }
      }
    }
  }

  override fun onDestroyView() {
    binding.mediaListRecyclerview.adapter = null
    super.onDestroyView()
  }
}