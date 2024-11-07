package com.example.moviedocs.presentation

import android.os.Bundle
import android.view.View
import com.example.moviedocs.databinding.FragmentSignUpBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.utils.navigateBack

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setUpNavigate()
  }
  
  private fun setUpNavigate() {
    binding.backBtn.navigateBack()
  }
}