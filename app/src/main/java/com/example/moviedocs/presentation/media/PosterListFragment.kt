package com.example.moviedocs.presentation.media

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.FragmentMediaListBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.utils.download.Downloader
import com.example.moviedocs.utils.download.DownloaderImpl
import com.example.moviedocs.utils.setUpRecyclerView

class PosterListFragment : BaseFragment<FragmentMediaListBinding>(
  FragmentMediaListBinding::inflate
) {

  private val viewModel: MediaListViewModel by viewModels()

  private val downloader: Downloader by lazy(LazyThreadSafetyMode.NONE) {
    DownloaderImpl(requireContext())
  }

  private val adapter: MediaListVerticalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MediaListVerticalAdapter(MediaType.BACKDROP, downloader)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.mediaListTitle.text = "Backdrop List"
    setUpRecyclerView(
      mRecyclerView = binding.mediaListRecyclerview,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.VERTICAL, false
      ),
      mAdapter = adapter
    )
  }
}