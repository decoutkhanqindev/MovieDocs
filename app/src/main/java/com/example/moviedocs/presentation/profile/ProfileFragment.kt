package com.example.moviedocs.presentation.profile

import com.example.moviedocs.databinding.FragmentProfileBinding
import com.example.moviedocs.presentation.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
  
  companion object {
    fun newInstance(): ProfileFragment = ProfileFragment()
    
  }
}