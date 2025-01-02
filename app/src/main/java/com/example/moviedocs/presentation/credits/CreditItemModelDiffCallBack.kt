package com.example.moviedocs.presentation.credits

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedocs.domain.model.credits.CreditItemModel

internal object CreditItemModelDiffCallBack : DiffUtil.ItemCallback<CreditItemModel>() {
  override fun areItemsTheSame(
    oldItem: CreditItemModel, newItem: CreditItemModel
  ): Boolean = oldItem.id == newItem.id

  override fun areContentsTheSame(
    oldItem: CreditItemModel, newItem: CreditItemModel
  ): Boolean = oldItem == newItem
}