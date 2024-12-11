package com.example.moviedocs.presentation.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviedocs.presentation.moviedetail.credits.MovieDetailCreditsFragment
import com.example.moviedocs.presentation.moviedetail.overview.MovieDetailOverviewFragment

class MovieDetailViewPagerAdapter(
  fragment: Fragment,
  private val movieId: Int
) : FragmentStateAdapter(fragment) {

  override fun getItemCount(): Int = 2

  override fun createFragment(position: Int): Fragment = when (position) {
    0 -> MovieDetailOverviewFragment.newInstance()
    1 -> {
      val fragment: MovieDetailCreditsFragment = MovieDetailCreditsFragment.newInstance()
      val bundle: Bundle = Bundle().apply { putInt("movieId", movieId) }
      fragment.arguments = bundle
      fragment
    }

    else -> throw IllegalArgumentException("Invalid $position")
  }
}