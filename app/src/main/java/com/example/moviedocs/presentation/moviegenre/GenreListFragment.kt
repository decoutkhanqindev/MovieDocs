package com.example.moviedocs.presentation.moviegenre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.FragmentGenreListBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.navigateBack
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreListFragment : BaseFragment<FragmentGenreListBinding>(
  FragmentGenreListBinding::inflate
) {
  companion object {
    fun newInstance(): GenreListFragment = GenreListFragment()
  }

  private val viewModel: GenreListViewModel by viewModels()

  private val adapter: GenreListAdapter by lazy(LazyThreadSafetyMode.NONE) {
    GenreListAdapter()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setUpNavigation()
    setUpRecyclerView(
      mRecyclerView = binding.genreRecycleView,
      mLayoutManager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false),
      mAdapter = adapter
    )
    bindViewModel()
  }

  private fun setUpNavigation() {
    binding.backBtn.navigateBack()
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  private fun renderUi(state: GenreListUiState) {
    when (state) {
      GenreListUiState.Loading -> {
        binding.apply {
          genreListProgressBar.visible()
          genreRecycleView.invisible()
        }
      }

      is GenreListUiState.Success -> {
        binding.apply {
          genreListProgressBar.invisible()
          genreRecycleView.visible()
        }
        adapter.submitList(state.items)
      }

      is GenreListUiState.Error -> {
        binding.apply {
          genreListProgressBar.visible()
          genreRecycleView.invisible()
        }
      }
    }
  }
}