package com.example.moviedocs.presentation.movielist

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedocs.domain.model.movielist.MovieItemModel

internal object MovieItemModelDiffCallBack : DiffUtil.ItemCallback<MovieItemModel>() {
  override fun areContentsTheSame(
    oldItem: MovieItemModel, newItem: MovieItemModel,
  ): Boolean = oldItem == newItem

  override fun areItemsTheSame(
    oldItem: MovieItemModel, newItem: MovieItemModel,
  ): Boolean = oldItem.id == newItem.id
}