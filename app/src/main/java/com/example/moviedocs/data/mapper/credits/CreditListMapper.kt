package com.example.moviedocs.data.mapper.credits

import com.example.moviedocs.data.remote.response.credits.CreditListResponse
import com.example.moviedocs.domain.model.credits.CreditListModel

fun CreditListResponse.toCreditListModel(): CreditListModel =
  CreditListModel(
    cast = cast.map { it.toCreditItemModel() },
    crew = crew.map { it.toCreditItemModel() },
    id = id
  )