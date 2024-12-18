package com.example.moviedocs.presentation.person.overview

import com.example.moviedocs.databinding.FragmentPersonDetailOverviewBinding
import com.example.moviedocs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailOverviewFragment : BaseFragment<FragmentPersonDetailOverviewBinding>(
  FragmentPersonDetailOverviewBinding::inflate
) {
  companion object {
    fun newInstance(): PersonDetailOverviewFragment = PersonDetailOverviewFragment()
  }
}