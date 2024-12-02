package com.example.moviedocs.data.mapper.moviedetail.language

import com.example.moviedocs.data.remote.response.moviedetails.language.LanguageItemResponse
import com.example.moviedocs.domain.model.moviedetail.language.LanguageItemModel

fun LanguageItemResponse.toLanguageItemModel(): LanguageItemModel =
  LanguageItemModel(
    iso6391 = iso6391,
    englishName = englishName ?: "No English Name",
    name = name ?: "No Name",
  )