package com.example.moviedocs.presentation.moviegenre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedocs.databinding.FragmentGenreListBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.utils.gone
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.navigateBack
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
    setUpRecyclerView()
    bindViewModel()
  }
  
  private fun setUpNavigation() {
    binding.backBtn.navigateBack()
  }
  
  private fun setUpRecyclerView() {
    binding.genreRecycleView.apply {
      setHasFixedSize(true)
      layoutManager = GridLayoutManager(
        requireContext(), 3
      )
      adapter = this@GenreListFragment.adapter
    }
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
        adapter.submitList(emptyList())
      }
      
      is GenreListUiState.Error -> {
        binding.apply {
          genreListProgressBar.visible()
          genreRecycleView.invisible()
        }
        adapter.submitList(emptyList())
      }
      
      is GenreListUiState.Success -> {
        binding.apply {
          genreListProgressBar.gone()
          genreRecycleView.visible()
        }
        adapter.submitList(state.items)
      }
    }
  }
}