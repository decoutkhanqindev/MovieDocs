package com.example.moviedocs.data.repository.slider

import com.example.moviedocs.R
import com.example.moviedocs.di.AppDispatcher
import com.example.moviedocs.di.DispatcherType
import com.example.moviedocs.domain.model.slider.SliderImageModel
import com.example.moviedocs.domain.repository.slider.SliderRepository
import com.example.moviedocs.utils.runSuspendCatching
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class SliderRepositoryImpl @Inject constructor(
  @AppDispatcher(DispatcherType.IO)
  private val ioDispatcher: CoroutineDispatcher
) : SliderRepository {
  override suspend fun getSliderImage(): Result<List<SliderImageModel>> =
    runSuspendCatching(ioDispatcher) {
      listOf(
        SliderImageModel(1, R.drawable.wide),
        SliderImageModel(2, R.drawable.wide1),
        SliderImageModel(3, R.drawable.wide3)
      )
    }
}