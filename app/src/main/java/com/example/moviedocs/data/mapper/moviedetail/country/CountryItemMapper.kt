package com.example.moviedocs.data.mapper.moviedetail.country

import com.example.moviedocs.data.remote.response.moviedetails.counttry.CountryItemResponse
import com.example.moviedocs.domain.model.moviedetail.country.CountryItemModel

fun CountryItemResponse.toCountryItemModel(): CountryItemModel =
  CountryItemModel(
    iso31661 = iso31661,
    englishName = englishName ?: "No english name",
    nativeName = nativeName ?: "No native name",
  )