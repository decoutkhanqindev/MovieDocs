package com.example.moviedocs.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.moviedocs.R
import com.example.moviedocs.databinding.FragmentSignInBinding
import com.example.moviedocs.presentation.base.BaseFragment

class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    binding.signUpText.setOnClickListener {
      findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
    }
  }
}