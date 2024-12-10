package com.example.moviedocs.domain.model.credits

data class CreditListModel(
  val cast: List<CastItemModel>,
  val crew: List<CrewItemModel>,
  val id: Int // movie id
)