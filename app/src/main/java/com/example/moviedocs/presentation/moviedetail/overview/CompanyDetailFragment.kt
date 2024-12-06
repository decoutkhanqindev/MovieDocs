package com.example.moviedocs.presentation.moviedetail.overview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.FragmentCompanyDetailBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.movielist.MovieListHorizontalAdapter
import com.example.moviedocs.presentation.movielist.MovieListUiState
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.loadImgFromUrl
import com.example.moviedocs.utils.navigateBack
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.toCountryName
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CompanyDetailFragment : BaseFragment<FragmentCompanyDetailBinding>(
  FragmentCompanyDetailBinding::inflate
) {

  private val args: CompanyDetailFragmentArgs by navArgs()

  private val viewModel: CompanyDetailViewModel by viewModels()

  private val movieListAdapter: MovieListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListHorizontalAdapter()
  }

  private lateinit var homepage: String

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.getCompanyDetail(companyId = args.companyId)
    viewModel.getCompanyMoveList(page = 1, companyId = args.companyId)
    setUpNavigation()
    setUpRecyclerView(
      binding.movieListRecycleView,
      LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
      movieListAdapter
    )
    bindViewModel()
  }

  private fun setUpNavigation() {
    binding.apply {
      backBtn.navigateBack()

      movieDetailLinkImg.setOnClickListener {
        val url: String = homepage
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
      }
    }
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.combinedUiState.collectLatest(::renderUi) })
  }

  private fun renderUi(state: Pair<CompanyDetailUiState, MovieListUiState>) {
    val (companyState: CompanyDetailUiState, movieListState: MovieListUiState) = state

    when (companyState) {
      CompanyDetailUiState.Loading -> {
        binding.apply {
          progressBar.visible()
          mainLayout.invisible()
        }
      }

      is CompanyDetailUiState.Success -> {
        binding.apply {
          progressBar.invisible()
          mainLayout.visible()

          homepage = companyState.companyDetail.homepage
          movieDetailImg.loadImgFromUrl(companyState.companyDetail.logoPath)
          companyNameValue.text = companyState.companyDetail.name
          headquarterValue.text = companyState.companyDetail.headquarters
          originalCountryValue.text =
            companyState.companyDetail.originCountry.toCountryName(companyState.countryList)
          companyDetailDescription.text = companyState.companyDetail.description
          parentValue.text = companyState.companyDetail.parentCompany
        }
      }

      is CompanyDetailUiState.Error -> {
        binding.apply {
          progressBar.visible()
          mainLayout.invisible()
        }
      }
    }

    when (movieListState) {
      MovieListUiState.Loading -> {
        binding.apply {
          progressBar.visible()
          mainLayout.invisible()
        }
      }

      is MovieListUiState.Success -> {
        binding.apply {
          progressBar.invisible()
          mainLayout.visible()
        }
        movieListAdapter.submitList(movieListState.items)
      }

      is MovieListUiState.Error -> {
        binding.apply {
          progressBar.visible()
          mainLayout.invisible()
        }
      }
    }
  }
}