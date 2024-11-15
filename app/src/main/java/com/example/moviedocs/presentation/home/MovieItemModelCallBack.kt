package com.example.moviedocs.presentation.home

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedocs.domain.model.list.MovieItemModel

object MovieItemModelCallBack : DiffUtil.ItemCallback<MovieItemModel>() {
  override fun areContentsTheSame(
    oldItem: MovieItemModel, newItem: MovieItemModel,
  ): Boolean = oldItem == newItem
  
  override fun areItemsTheSame(
    oldItem: MovieItemModel, newItem: MovieItemModel,
  ): Boolean = oldItem.id == newItem.id
}