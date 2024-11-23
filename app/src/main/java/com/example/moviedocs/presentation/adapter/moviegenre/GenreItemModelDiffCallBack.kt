package com.example.moviedocs.presentation.adapter.moviegenre

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedocs.domain.model.moviegenre.GenreItemModel

class GenreItemModelDiffCallBack : DiffUtil.ItemCallback<GenreItemModel>() {
  override fun areContentsTheSame(oldItem: GenreItemModel, newItem: GenreItemModel): Boolean =
    oldItem == newItem
  
  override fun areItemsTheSame(oldItem: GenreItemModel, newItem: GenreItemModel): Boolean =
    oldItem.id == newItem.id
}