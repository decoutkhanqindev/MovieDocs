package com.example.moviedocs.presentation.favorite

import android.os.Bundle
import android.view.View
import com.example.moviedocs.databinding.FragmentFavoriteBinding
import com.example.moviedocs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

  companion object {
    fun newInstance(): FavoriteFragment = FavoriteFragment()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }
}