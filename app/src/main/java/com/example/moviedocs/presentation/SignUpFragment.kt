package com.example.moviedocs.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.moviedocs.R
import com.example.moviedocs.databinding.FragmentSignUpBinding
import com.example.moviedocs.presentation.base.BaseFragment

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    binding.backBtn.setOnClickListener {
      findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }
  }
}