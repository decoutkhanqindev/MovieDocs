package com.example.moviedocs.domain.repository.slider

import com.example.moviedocs.domain.model.slider.SliderImageModel

interface SliderRepository {
  suspend fun getSliderImage(): Result<List<SliderImageModel>>
}