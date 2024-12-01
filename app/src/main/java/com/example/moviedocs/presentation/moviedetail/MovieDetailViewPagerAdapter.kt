package com.example.moviedocs.presentation.moviedetail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MovieDetailViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
  
  override fun getItemCount(): Int = 2
  
  override fun createFragment(position: Int): Fragment = when (position) {
    0 -> MovieDetailOverviewFragment.newInstance()
    1 -> MovieDetailCastAndCrewFragment.newInstance()
    else -> throw IllegalArgumentException("Invalid $position")
  }
}