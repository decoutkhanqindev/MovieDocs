package com.example.moviedocs.presentation.moviedetail.credits

import android.os.Bundle
import android.view.View
import com.example.moviedocs.databinding.FragmentMovieDetailCreditsBinding
import com.example.moviedocs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailCreditsFragment : BaseFragment<FragmentMovieDetailCreditsBinding>(
  FragmentMovieDetailCreditsBinding::inflate
) {
  companion object {
    fun newInstance(): MovieDetailCreditsFragment = MovieDetailCreditsFragment()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }
}