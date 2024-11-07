package com.example.moviedocs.presentation

import android.os.Bundle
import android.view.View
import com.example.moviedocs.R
import com.example.moviedocs.databinding.FragmentSignInBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.utils.navigateTo

class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setUpNavigate()
  }
  
  private fun setUpNavigate() {
    binding.signInBtn.navigateTo(R.id.action_signInFragment_to_moviesFragment)
    binding.signUpBtn.navigateTo(R.id.action_signInFragment_to_signUpFragment)
  }
}