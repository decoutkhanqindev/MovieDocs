package com.example.moviedocs.presentation.credits.crew

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.R
import com.example.moviedocs.databinding.FragmentCreditListBinding
import com.example.moviedocs.domain.model.credits.CrewItemModel
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.credits.CreditListUiState
import com.example.moviedocs.presentation.credits.CreditListViewModel
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

  private val viewModel: CreditListViewModel by activityViewModels()

  private val crewVerticalAdapter: CrewListVerticalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    CrewListVerticalAdapter()
  }

  @SuppressLint("SetTextI18n")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    if (savedInstanceState == null) {
      viewModel.loadCreditList(movieId = args.movieId)
    }
    binding.creditListTitle.text = getString(R.string.crew_list_title)
    setUpNavigation()
    setUpRecyclerView(
      mRecyclerView = binding.creditListRecyclerView,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.VERTICAL, false
      ),
      mAdapter = crewVerticalAdapter
    )
    bindViewModel()
    handleSortingList()
  }

  private fun setUpNavigation() {
    binding.backBtn.navigateBack()

    crewVerticalAdapter.setOnItemClickListener { it: CrewItemModel ->
      findNavController().navigate(
        CrewListFragmentDirections.actionCrewListFragmentToPersonDetailFragment(
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
        binding.apply {
          creditListProgressBar.visible()
          creditListRecyclerView.invisible()
        }
      }

      is CreditListUiState.Success -> {
        binding.apply {
          creditListProgressBar.invisible()
          creditListRecyclerView.visible()
        }
        crewVerticalAdapter.submitList(state.crewList)
      }

      is CreditListUiState.Error -> {
        binding.apply {
          creditListProgressBar.visible()
          creditListRecyclerView.invisible()
        }
      }
    }
  }

  private fun handleSortingList() {
    binding.toolBar.setOnMenuItemClickListener { it: MenuItem ->
      when (it.itemId) {
        R.id.creditTitleAsc -> {
          viewModel.sortCrewList(CreditListUiState.SortType.TITLE_ASC)
          true
        }

        R.id.creditTitleDsc -> {
          viewModel.sortCrewList(CreditListUiState.SortType.TITLE_DSC)
          true
        }

        R.id.creditPopularityAsc -> {
          viewModel.sortCrewList(CreditListUiState.SortType.POPULARITY_ASC)
          true
        }

        R.id.creditPopularityDsc -> {
          viewModel.sortCrewList(CreditListUiState.SortType.POPULARITY_DSC)
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