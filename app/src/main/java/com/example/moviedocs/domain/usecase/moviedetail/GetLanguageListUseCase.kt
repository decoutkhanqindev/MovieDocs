package com.example.moviedocs.domain.usecase.moviedetail

import com.example.moviedocs.domain.model.moviedetail.language.LanguageItemModel
import com.example.moviedocs.domain.repository.moviedetail.MovieDetailRepository
import javax.inject.Inject

class GetLanguageListUseCase @Inject constructor(
  private val repository: MovieDetailRepository,
) {
  suspend operator fun invoke(): Result<List<LanguageItemModel>> =
    repository.getLanguageList()
}