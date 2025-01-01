package com.example.moviedocs.presentation.media

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedocs.domain.model.media.MediaItemModel

object MediaItemModelDiffCallback : DiffUtil.ItemCallback<MediaItemModel>() {
  override fun areItemsTheSame(
    oldItem: MediaItemModel,
    newItem: MediaItemModel
  ): Boolean = oldItem.filePath == newItem.filePath

  override fun areContentsTheSame(
    oldItem: MediaItemModel,
    newItem: MediaItemModel
  ): Boolean = oldItem == newItem
}