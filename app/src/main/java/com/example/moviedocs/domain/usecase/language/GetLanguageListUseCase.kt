package com.example.moviedocs.domain.usecase.language

import com.example.moviedocs.domain.model.language.LanguageItemModel
import com.example.moviedocs.domain.repository.language.LanguageRepository
import javax.inject.Inject

class GetLanguageListUseCase @Inject constructor(
  private val repository: LanguageRepository,
) {
  suspend operator fun invoke(): Result<List<LanguageItemModel>> =
    repository.getLanguageList()
}