package com.example.moviedocs.domain.usecase.credits

import com.example.moviedocs.domain.model.credits.CreditListModel
import com.example.moviedocs.domain.repository.credits.CreditsRepository
import javax.inject.Inject

class GetCreditListUseCase @Inject constructor(
  private val repository: CreditsRepository,
) {
  suspend operator fun invoke(movieId: Int): Result<CreditListModel> =
    repository.getCreditList(movieId = movieId)
}