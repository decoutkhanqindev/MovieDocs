package com.example.moviedocs.presentation.search

import android.os.Bundle
import android.view.View
import com.example.moviedocs.databinding.FragmentSearchBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.utils.navigateBack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setUpNavigation()
  }

  private fun setUpNavigation() {
    binding.backBtn.navigateBack()
  }
}