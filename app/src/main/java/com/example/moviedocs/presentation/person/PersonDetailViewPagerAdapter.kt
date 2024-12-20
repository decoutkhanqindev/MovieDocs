package com.example.moviedocs.presentation.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviedocs.presentation.person.moviecredits.cast.PersonDetailMovieCastFragment
import com.example.moviedocs.presentation.person.moviecredits.credit.PersonDetailMovieCrewFragment
import com.example.moviedocs.presentation.person.overview.PersonDetailOverviewFragment

class PersonDetailViewPagerAdapter(fragment: Fragment, private val personId: Int) :
  FragmentStateAdapter(fragment) {

  override fun getItemCount(): Int = 3

  override fun createFragment(position: Int): Fragment = when (position) {
    0 -> setArgument(PersonDetailOverviewFragment.newInstance(), personId)
    1 -> setArgument(PersonDetailMovieCastFragment.newInstance(), personId)
    2 -> setArgument(PersonDetailMovieCrewFragment.newInstance(), personId)
    else -> throw IllegalArgumentException("Invalid $position")
  }

  private fun setArgument(fragment: Fragment, personId: Int): Fragment {
    val fragment: Fragment = fragment
    val bundle: Bundle = Bundle().apply { putInt("personId", personId) }
    fragment.arguments = bundle
    return fragment
  }
}