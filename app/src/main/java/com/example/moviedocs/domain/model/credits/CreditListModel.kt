package com.example.moviedocs.domain.model.credits

data class CreditListModel(
  val cast: List<CreditItemModel>,
  val crew: List<CreditItemModel>,
  val id: Int // movie id
)