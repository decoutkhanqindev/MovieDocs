package com.example.moviedocs.presentation.person.moviecredits

import com.example.moviedocs.databinding.FragmentPersonDetailMovieCreditsBinding
import com.example.moviedocs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailMovieCreditsFragment : BaseFragment<FragmentPersonDetailMovieCreditsBinding>(
  FragmentPersonDetailMovieCreditsBinding::inflate
) {
  companion object {
    fun newInstance(): PersonDetailMovieCreditsFragment = PersonDetailMovieCreditsFragment()
  }
}