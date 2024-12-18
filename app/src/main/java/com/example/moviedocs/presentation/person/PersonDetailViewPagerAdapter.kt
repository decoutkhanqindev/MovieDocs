package com.example.moviedocs.presentation.person

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviedocs.presentation.person.moviecredits.PersonDetailMovieCreditsFragment
import com.example.moviedocs.presentation.person.overview.PersonDetailOverviewFragment

class PersonDetailViewPagerAdapter(
  fragment: Fragment,
  private val personId: Int
) : FragmentStateAdapter(fragment) {

  override fun getItemCount(): Int = 2

  override fun createFragment(position: Int): Fragment = when (position) {
    0 -> PersonDetailOverviewFragment.newInstance()
    1 -> PersonDetailMovieCreditsFragment.newInstance()

    else -> throw IllegalArgumentException("Invalid $position")
  }
}