package com.example.moviedocs.domain.repository.credits

import com.example.moviedocs.domain.model.credits.CreditListModel

interface CreditsRepository {
  suspend fun getCreditList(movieId: Int): Result<CreditListModel>
}