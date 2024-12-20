package com.example.moviedocs.domain.repository.slider

import com.example.moviedocs.domain.model.slider.SliderImageItemModel

interface SliderRepository {
  suspend fun getSliderImageList(): Result<List<SliderImageItemModel>>
}