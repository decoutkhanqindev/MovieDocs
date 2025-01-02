package com.example.moviedocs.presentation.auth

import android.os.Bundle
import android.view.View
import com.example.moviedocs.databinding.FragmentSignUpBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.utils.navigateBack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

  companion object {
    fun newInstance(): SignUpFragment = SignUpFragment()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setUpNavigation()
  }

  private fun setUpNavigation() {
    binding.backBtn.navigateBack()
  }
}