package com.example.moviedocs.presentation.profile

import android.os.Bundle
import android.view.View
import com.example.moviedocs.databinding.FragmentProfileBinding
import com.example.moviedocs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

  companion object {
    fun newInstance(): ProfileFragment = ProfileFragment()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }
}