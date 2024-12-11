package com.example.moviedocs.presentation.credits.crew

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedocs.domain.model.credits.CrewItemModel

object CrewItemModelDiffCallBack : DiffUtil.ItemCallback<CrewItemModel>() {
  override fun areItemsTheSame(
    oldItem: CrewItemModel,
    newItem: CrewItemModel
  ): Boolean = oldItem.id == newItem.id

  override fun areContentsTheSame(
    oldItem: CrewItemModel,
    newItem: CrewItemModel
  ): Boolean = oldItem == newItem
}