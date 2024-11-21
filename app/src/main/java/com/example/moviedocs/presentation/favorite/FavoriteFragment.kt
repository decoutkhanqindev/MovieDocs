package com.example.moviedocs.presentation.favorite

import android.os.Bundle
import android.view.View
import com.example.moviedocs.databinding.FragmentFavoriteBinding
import com.example.moviedocs.presentation.base.BaseFragment

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {
  
  companion object {
    fun newInstance(): FavoriteFragment = FavoriteFragment()
    const val TAG: String = "HomeFragment"
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }
}