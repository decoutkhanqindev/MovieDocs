package com.example.moviedocs.data.mapper.language

import com.example.moviedocs.data.remote.response.language.LanguageItemResponse
import com.example.moviedocs.domain.model.language.LanguageItemModel

fun LanguageItemResponse.toLanguageItemModel(): LanguageItemModel =
  LanguageItemModel(
    iso6391 = iso6391,
    englishName = englishName?.takeIf { it.isNotEmpty() } ?: "No english name",
    name = name?.takeIf { it.isNotEmpty() } ?: "No name"
  )