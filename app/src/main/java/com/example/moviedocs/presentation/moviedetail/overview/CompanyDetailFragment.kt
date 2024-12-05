package com.example.moviedocs.presentation.moviedetail.overview

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.moviedocs.databinding.FragmentCompanyDetailBinding
import com.example.moviedocs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CompanyDetailFragment: BaseFragment<FragmentCompanyDetailBinding>(
  FragmentCompanyDetailBinding::inflate
) {

  private val args: CompanyDetailFragmentArgs by navArgs()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    Timber.d("companyId: ${args.companyId}")
  }
}