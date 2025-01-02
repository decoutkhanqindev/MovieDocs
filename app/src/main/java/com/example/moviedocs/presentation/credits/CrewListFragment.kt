package com.example.moviedocs.presentation.credits

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.R
import com.example.moviedocs.databinding.FragmentCreditListBinding
import com.example.moviedocs.domain.model.credits.CreditItemModel
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.navigateBack
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CrewListFragment : BaseFragment<FragmentCreditListBinding>(
  FragmentCreditListBinding::inflate
) {

  private val args: CrewListFragmentArgs by navArgs()

  private val viewModel: CreditListViewModel by viewModels()

  private val adapter: CreditListVerticalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    CreditListVerticalAdapter()
  }

  @SuppressLint("SetTextI18n")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.creditListTitle.text = getString(R.string.crew_list_title)
    setUpNavigation()
    setUpRecyclerView(
      mRecyclerView = binding.creditListRecyclerView,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.VERTICAL, false
      ),
      mAdapter = adapter
    )
    viewModel.setMovieId(movieId = args.movieId)
    bindViewModel()
    handleSortingList()
  }

  private fun setUpNavigation() {
    binding.backBtn.navigateBack()

    adapter.setOnItemClickListener { it: CreditItemModel ->
      findNavController().navigate(
        CrewListFragmentDirections.Companion.actionCrewListFragmentToPersonDetailFragment(
          personId = it.id
        )
      )
    }
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  private fun renderUi(state: CreditListUiState) {
    when (state) {
      CreditListUiState.Loading -> {
        hideErrorDialog()

        binding.apply {
          creditListProgressBar.visible()
          creditListRecyclerView.invisible()
        }
      }

      is CreditListUiState.Success -> {
        hideErrorDialog()

        binding.apply {
          creditListProgressBar.invisible()
          creditListRecyclerView.visible()
        }
        adapter.submitList(state.crewList)
      }

      is CreditListUiState.Error -> {
        binding.apply {
          creditListProgressBar.visible()
          creditListRecyclerView.invisible()
        }

        showErrorDialog { viewModel.loadCreditList(args.movieId) }
      }
    }
  }

  private fun handleSortingList() {
    binding.toolBar.setOnMenuItemClickListener { it: MenuItem ->
      when (it.itemId) {
        R.id.creditTitleAsc -> {
          viewModel.sortCreditList(CreditType.CREW, CreditListUiState.SortType.TITLE_ASC)
          true
        }

        R.id.creditTitleDsc -> {
          viewModel.sortCreditList(CreditType.CREW, CreditListUiState.SortType.TITLE_DSC)
          true
        }

        R.id.creditPopularityAsc -> {
          viewModel.sortCreditList(CreditType.CREW, CreditListUiState.SortType.POPULARITY_ASC)
          true
        }

        R.id.creditPopularityDsc -> {
          viewModel.sortCreditList(CreditType.CREW, CreditListUiState.SortType.POPULARITY_DSC)
          true
        }

        else -> false
      }
    }
  }

  override fun onDestroyView() {
    binding.creditListRecyclerView.adapter = null
    super.onDestroyView()
  }
}