package com.example.moviedocs.presentation.home

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedocs.domain.model.slider.SliderImageItemModel

internal object SliderImgItemModelDiffCallBack : DiffUtil.ItemCallback<SliderImageItemModel>() {
  override fun areContentsTheSame(
    oldItem: SliderImageItemModel, newItem: SliderImageItemModel,
  ): Boolean = oldItem == newItem

  override fun areItemsTheSame(
    oldItem: SliderImageItemModel, newItem: SliderImageItemModel,
  ): Boolean = oldItem.id == newItem.id
}