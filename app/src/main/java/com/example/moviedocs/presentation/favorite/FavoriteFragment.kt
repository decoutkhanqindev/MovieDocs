package com.example.moviedocs.presentation.favorite

import com.example.moviedocs.databinding.FragmentFavoriteBinding
import com.example.moviedocs.presentation.base.BaseFragment

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {
  
  companion object {
    fun newInstance(): FavoriteFragment = FavoriteFragment()
  }
}