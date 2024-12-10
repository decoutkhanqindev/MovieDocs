package com.example.moviedocs.presentation.credits

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedocs.domain.model.credits.CastItemModel

object CastItemDiffCallBack : DiffUtil.ItemCallback<CastItemModel>() {
  override fun areItemsTheSame(
    oldItem: CastItemModel,
    newItem: CastItemModel
  ): Boolean = oldItem.id == newItem.id

  override fun areContentsTheSame(
    oldItem: CastItemModel,
    newItem: CastItemModel
  ): Boolean = oldItem == newItem
}