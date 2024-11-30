package com.example.moviedocs.presentation.moviedetail

import android.os.Bundle
import android.view.View
import com.example.moviedocs.databinding.FragmentMovieDetailOverviewBinding
import com.example.moviedocs.presentation.base.BaseFragment

class MovieDetailOverviewFragment : BaseFragment<FragmentMovieDetailOverviewBinding>(
  FragmentMovieDetailOverviewBinding::inflate
){
  
  companion object {
    fun newInstance(): MovieDetailOverviewFragment = MovieDetailOverviewFragment()
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }
}