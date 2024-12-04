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
  
  private lateinit var fbId: String
  private lateinit var igId: String
  private lateinit var twId: String
  
  private val args: MovieDetailFragmentArgs by navArgs()
  
  private val viewModel: MovieDetailViewModel by activityViewModels()
  
  private val viewPagerAdapter: MovieDetailViewPagerAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieDetailViewPagerAdapter(fragment = this)
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    viewModel.getMovieDetail(movieId = args.movieId)
    setUpNavigation()
    setUpViewPagerTabLayout()
    bindViewModel()
  }
  
  private fun setUpNavigation() {
    binding.apply {
      backBtn.navigateBack()
      
      movieDetailFbImg.setOnClickListener {
        val fbUrl = "$FB_URL$fbId"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fbUrl))
        startActivity(intent)
      }
      
      movieDetailIgImg.setOnClickListener {
        val igUrl = "$IG_URL$igId"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(igUrl))
        startActivity(intent)
      }
      
      movieDetailTwitterImg.setOnClickListener {
        val twitterUrl = "$TWITTER_URL$twId"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl))
        startActivity(intent)
      }
    }
  }
  
  private fun setUpViewPagerTabLayout() {
    binding.movieDetailViewPager.apply {
      adapter = viewPagerAdapter
      isUserInputEnabled = true
      TabLayoutMediator(binding.tabLayout, this) { tab: TabLayout.Tab, position: Int ->
        tab.text = when (position) {
          0 -> "Overview"
          1 -> "Cast & Crew"
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
        binding.apply {          progressBar.visible()
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

//          state.apply {
//            movieDetailImg.loadImgFromUrl(movieDetail.posterPath)
//            movieDetailTitle.text = movieDetail.title
//            movieDetailReleaseDate.text = movieDetail.releaseDate
//            movieDetailRuntime.text =
//              movieDetail.runtime.convertMinutesToHoursAndMinutes()
//            movieDetailRatingVoteAverage.text =
//              "%.1f".format(movieDetail.voteAverage)
//            movieDetailRatingVoteCount.text = "${movieDetail.voteCount}"
//
//            fbId = externalIds.facebookId
//            igId = externalIds.instagramId
//            twId = externalIds.twitterId
//          }
          
          movieDetailImg.loadImgFromUrl(state.movieDetail.posterPath)
          movieDetailTitle.text = state.movieDetail.title
          movieDetailReleaseDate.text = state.movieDetail.releaseDate
          movieDetailRuntime.text = state.movieDetail.runtime.convertMinutesToHoursAndMinutes()
          movieDetailRatingVoteAverage.text = "%.1f".format(state.movieDetail.voteAverage)
          movieDetailRatingVoteCount.text = "${state.movieDetail.voteCount}"
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
}