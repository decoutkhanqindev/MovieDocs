package com.example.moviedocs.presentation.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviedocs.presentation.moviedetail.credits.MovieDetailCreditsFragment
import com.example.moviedocs.presentation.moviedetail.media.MovieDetailMediaFragment
import com.example.moviedocs.presentation.moviedetail.overview.MovieDetailOverviewFragment

class MovieDetailViewPagerAdapter(
  fragment: Fragment, private val movieId: Int
) : FragmentStateAdapter(fragment) {

  override fun getItemCount(): Int = 3

  override fun createFragment(position: Int): Fragment = when (position) {
    0 -> setArgument(MovieDetailOverviewFragment.newInstance(), movieId)
    1 -> setArgument(MovieDetailCreditsFragment.newInstance(), movieId)
    2 -> setArgument(MovieDetailMediaFragment.newInstance(), movieId)
    else -> throw IllegalArgumentException("Invalid $position")
  }

  private fun setArgument(fragment: Fragment, movieId: Int): Fragment {
    val fragment: Fragment = fragment
    val bundle: Bundle = Bundle().apply { putInt("movieId", movieId) }
    fragment.arguments = bundle
    return fragment
  }
}