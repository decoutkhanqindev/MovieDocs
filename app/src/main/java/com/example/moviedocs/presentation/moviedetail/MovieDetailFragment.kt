package com.example.moviedocs.presentation.moviedetail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.moviedocs.databinding.FragmentMovieDetailBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.utils.convertMinutesToHoursAndMinutes
import com.example.moviedocs.utils.formatDate
import com.example.moviedocs.utils.formatVoteAverage
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.loadImgFromUrl
import com.example.moviedocs.utils.navigateBack
import com.example.moviedocs.utils.visible
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(
  FragmentMovieDetailBinding::inflate
) {

  companion object {
    private const val FB_URL: String = "https://www.facebook.com/"
    private const val IG_URL: String = "https://www.instagram.com/"
    private const val TWITTER_URL: String = "https://www.twitter.com/"
  }

  private lateinit var homepage: String
  private lateinit var fbId: String
  private lateinit var igId: String
  private lateinit var twId: String

  private val args: MovieDetailFragmentArgs by navArgs()

  private val viewModel: MovieDetailViewModel by activityViewModels()

  private lateinit var viewPagerAdapter: MovieDetailViewPagerAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewPagerAdapter = MovieDetailViewPagerAdapter(fragment = this, movieId = args.movieId)
    viewModel.loadData(args.movieId)
    setUpNavigation()
    setUpTabLayout()
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
      movieDetailFbImg.setOnClickListener { openSocialMedia(FB_URL, fbId) }
      movieDetailIgImg.setOnClickListener { openSocialMedia(IG_URL, igId) }
      movieDetailTwitterImg.setOnClickListener { openSocialMedia(TWITTER_URL, twId) }
    }
  }

  private fun openSocialMedia(baseUrl: String, id: String) {
    val url = "$baseUrl$id"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent)
  }

  private fun setUpTabLayout() {
    binding.movieDetailViewPager.apply {
      if (adapter == null) {
        adapter = viewPagerAdapter
      }
      isUserInputEnabled = false
      TabLayoutMediator(binding.tabLayout, this) { tab: TabLayout.Tab, position: Int ->
        tab.text = when (position) {
          0 -> "Overview"
          1 -> "Credits"
          else -> throw IllegalArgumentException("Invalid $position")
        }
      }.attach()
    }
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  @SuppressLint("SetTextI18n")
  private fun renderUi(state: MovieDetailUiState) {
    when (state) {
      MovieDetailUiState.Loading -> {
        binding.apply {
          progressBar.visible()
          movieDetailTopLayout.invisible()
          tabLayout.invisible()
          movieDetailViewPager.invisible()
        }
      }

      is MovieDetailUiState.Success -> {
        binding.apply {
          progressBar.invisible()
          movieDetailTopLayout.visible()
          tabLayout.visible()
          movieDetailViewPager.visible()

          movieDetailImg.loadImgFromUrl(state.movieDetail.posterPath)
          movieDetailTitle.text = state.movieDetail.title
          movieDetailReleaseDate.text = state.movieDetail.releaseDate.formatDate()
          movieDetailRuntime.text = state.movieDetail.runtime.convertMinutesToHoursAndMinutes()
          movieDetailRatingVoteAverage.text = state.movieDetail.voteAverage.formatVoteAverage()
          movieDetailPopularity.text = state.movieDetail.popularity.toString()
          homepage = state.movieDetail.homepage
          fbId = state.externalIds.facebookId
          igId = state.externalIds.instagramId
          twId = state.externalIds.twitterId
        }
      }

      is MovieDetailUiState.Error -> {
        binding.apply {
          progressBar.visible()
          movieDetailTopLayout.invisible()
          tabLayout.invisible()
          movieDetailViewPager.invisible()
        }
      }
    }
  }

  override fun onDestroyView() {
    binding.movieDetailViewPager.adapter = null
    super.onDestroyView()
  }
}