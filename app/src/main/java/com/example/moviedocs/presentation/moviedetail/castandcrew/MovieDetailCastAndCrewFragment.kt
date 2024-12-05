package com.example.moviedocs.presentation.moviedetail.castandcrew

import android.os.Bundle
import android.view.View
import com.example.moviedocs.databinding.FragmentMovieDetailOverviewBinding
import com.example.moviedocs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailCastAndCrewFragment : BaseFragment<FragmentMovieDetailOverviewBinding>(
  FragmentMovieDetailOverviewBinding::inflate
) {
  companion object {
    fun newInstance(): MovieDetailCastAndCrewFragment = MovieDetailCastAndCrewFragment()
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }
}