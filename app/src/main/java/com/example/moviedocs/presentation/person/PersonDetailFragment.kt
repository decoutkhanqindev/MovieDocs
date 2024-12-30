package com.example.moviedocs.presentation.person

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.moviedocs.databinding.FragmentPersonDetailBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.utils.formatTotalResult
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.loadImgFromUrl
import com.example.moviedocs.utils.navigateBack
import com.example.moviedocs.utils.visible
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailFragment : BaseFragment<FragmentPersonDetailBinding>(
  FragmentPersonDetailBinding::inflate
) {

  companion object {
    private const val TIKTOK_URL: String = "https://www.tiktok.com/"
    private const val FB_URL: String = "https://www.facebook.com/"
    private const val IG_URL: String = "https://www.instagram.com/"
    private const val TWITTER_URL: String = "https://www.twitter.com/"
  }

  private val args: PersonDetailFragmentArgs by navArgs()

  private val viewModel: PersonDetailViewModel by viewModels()

  private lateinit var tiktokId: String
  private lateinit var fbId: String
  private lateinit var igId: String
  private lateinit var twId: String

  private var totalMoviesCast = 0
  private var totalMoviesCrew = 0

  private lateinit var viewPagerAdapter: PersonDetailViewPagerAdapter


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewPagerAdapter = PersonDetailViewPagerAdapter(fragment = this, args.personId)
    setUpNavigation()
    viewModel.setPersonId(args.personId)
    bindViewModel()
  }

  private fun setUpNavigation() {
    binding.apply {
      backBtn.navigateBack()

      personDetailTiktokImg.setOnClickListener { openSocialMedia(TIKTOK_URL, tiktokId) }
      personDetailFbImg.setOnClickListener { openSocialMedia(FB_URL, fbId) }
      personDetailIgImg.setOnClickListener { openSocialMedia(IG_URL, igId) }
      personDetailTwitterImg.setOnClickListener { openSocialMedia(TWITTER_URL, twId) }
    }
  }

  private fun openSocialMedia(baseUrl: String, id: String) {
    val url = "$baseUrl$id"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent)
  }

  private fun setUpViewPagerTabLayout() {
    binding.personDetailViewPager.apply {
      if (adapter == null) {
        adapter = viewPagerAdapter
      }
      isUserInputEnabled = false
      TabLayoutMediator(binding.tabLayout, this) { tab: TabLayout.Tab, position: Int ->
        tab.text = when (position) {
          0 -> "Overview"
          1 -> "Cast ${totalMoviesCast.formatTotalResult()}"
          2 -> "Crew ${totalMoviesCrew.formatTotalResult()}"
          else -> throw IllegalArgumentException("Invalid $position")
        }
      }.attach()
    }
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  @SuppressLint("SetTextI18n")
  private fun renderUi(state: PersonDetailUiState) {
    when (state) {
      PersonDetailUiState.Loading -> {
        binding.apply {
          progressBar.visible()
          personDetailTopLayout.invisible()
          tabLayout.invisible()
          personDetailViewPager.invisible()
        }
      }

      is PersonDetailUiState.Success -> {
        binding.apply {
          progressBar.invisible()
          personDetailTopLayout.visible()
          tabLayout.visible()
          personDetailViewPager.visible()

          personDetailName.text = state.personDetail.name
          personDetailImg.loadImgFromUrl(state.personDetail.profilePath)
          personDetailPopularity.text = state.personDetail.popularity.toString()
          tiktokId = state.personExternalIds.tiktokId
          fbId = state.personExternalIds.facebookId
          igId = state.personExternalIds.instagramId
          twId = state.personExternalIds.twitterId
          totalMoviesCast = state.castMovieCreditList.size
          totalMoviesCrew = state.crewMovieCreditList.size
        }
        setUpViewPagerTabLayout()
      }

      is PersonDetailUiState.Error -> {
        binding.apply {
          progressBar.visible()
          personDetailTopLayout.invisible()
          tabLayout.invisible()
          personDetailViewPager.invisible()
        }
      }
    }
  }

  override fun onDestroyView() {
    binding.personDetailViewPager.adapter = null
    super.onDestroyView()
  }
}