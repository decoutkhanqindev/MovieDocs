package com.example.moviedocs.presentation.list

import android.os.Bundle
import android.view.View
import com.example.moviedocs.databinding.FragmentSearchMoviesBinding
import com.example.moviedocs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMoviesFragment :
  BaseFragment<FragmentSearchMoviesBinding>(FragmentSearchMoviesBinding::inflate) {
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
  }
}