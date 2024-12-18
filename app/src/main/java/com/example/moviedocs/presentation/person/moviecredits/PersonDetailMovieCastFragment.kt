package com.example.moviedocs.presentation.person.moviecredits

import com.example.moviedocs.databinding.FragmentPersonDetailMovieCreditsBinding
import com.example.moviedocs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailMovieCastFragment : BaseFragment<FragmentPersonDetailMovieCreditsBinding>(
  FragmentPersonDetailMovieCreditsBinding::inflate
) {
  companion object {
    fun newInstance(): PersonDetailMovieCastFragment = PersonDetailMovieCastFragment()
  }
}