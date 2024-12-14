package com.example.moviedocs.data.mapper.country

import com.example.moviedocs.data.remote.response.country.CountryItemResponse
import com.example.moviedocs.domain.model.country.CountryItemModel

fun CountryItemResponse.toCountryItemModel(): CountryItemModel =
  CountryItemModel(
    iso31661 = iso31661,
    englishName = englishName?.takeIf { it.isNotEmpty() } ?: "",
    nativeName = nativeName?.takeIf { it.isNotEmpty() } ?: ""
  )