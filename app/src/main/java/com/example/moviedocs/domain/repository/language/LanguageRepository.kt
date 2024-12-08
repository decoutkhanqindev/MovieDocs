package com.example.moviedocs.domain.repository.language

import com.example.moviedocs.domain.model.language.LanguageItemModel

interface LanguageRepository {
  suspend fun getLanguageList(): Result<List<LanguageItemModel>>
}