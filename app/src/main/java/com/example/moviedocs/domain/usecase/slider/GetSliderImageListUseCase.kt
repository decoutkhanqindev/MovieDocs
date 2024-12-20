package com.example.moviedocs.domain.usecase.slider

import com.example.moviedocs.domain.model.slider.SliderImageItemModel
import com.example.moviedocs.domain.repository.slider.SliderRepository
import javax.inject.Inject

class GetSliderImageListUseCase @Inject constructor(
  private val repository: SliderRepository
) {
  suspend operator fun invoke(): Result<List<SliderImageItemModel>> =
    repository.getSliderImage()
}