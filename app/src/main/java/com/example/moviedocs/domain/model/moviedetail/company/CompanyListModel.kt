package com.example.moviedocs.domain.model.moviedetail.company

import androidx.annotation.Keep

@Keep
data class CompanyListModel(
  val results: List<CompanyItemModel>
)