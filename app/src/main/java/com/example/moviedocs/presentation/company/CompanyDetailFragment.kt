package com.example.moviedocs.presentation.company

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.FragmentCompanyDetailBinding
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.movielist.MovieListHorizontalAdapter
import com.example.moviedocs.presentation.movielist.MovieListUiState
import com.example.moviedocs.utils.formatTotalResult
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

  private lateinit var companyName: String

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

      movieListMoreBtn.setOnClickListener {
        findNavController().navigate(
          CompanyDetailFragmentDirections.actionCompanyDetailFragmentToCompanyMovieListFragment(
            companyId = args.companyId,
            companyName = companyName
          )
        )
      }

      movieListAdapter.onItemClickListener = { it: MovieItemModel ->
        findNavController().navigate(
          CompanyDetailFragmentDirections.actionCompanyDetailFragmentToMovieDetailFragment(
            movieId = it.id
          )
        )
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
          scrollView.invisible()
        }
      }

      is CompanyDetailUiState.Success -> {
        binding.apply {
          progressBar.invisible()
          scrollView.visible()

          homepage = companyState.companyDetail.homepage
          movieDetailImg.loadImgFromUrl(companyState.companyDetail.logoPath)
          companyNameValue.text = companyState.companyDetail.name
          companyName = companyState.companyDetail.name
          headquarterValue.text = companyState.companyDetail.headquarters
          originalCountryValue.text =
            companyState.companyDetail.originCountry.toCountryName(companyState.countryList)
          companyDetailDescription.text = companyState.companyDetail.description
          parentValue.text = companyState.companyDetail.parentCompany
        }

        when (movieListState) {
          MovieListUiState.Loading -> {}

          is MovieListUiState.Success -> {
            binding.totalMovies.text = movieListState.totalResults.formatTotalResult()
            movieListAdapter.submitList(movieListState.items)
          }

          is MovieListUiState.Error -> {}
        }
      }

      is CompanyDetailUiState.Error -> {
        binding.apply {
          progressBar.visible()
          scrollView.invisible()
        }
      }
    }
  }
}