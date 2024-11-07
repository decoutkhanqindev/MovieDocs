package com.example.moviedocs.presentation.list

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedocs.domain.model.MovieModel

object MovieModelItemCallBack : DiffUtil.ItemCallback<MovieModel>() {
  override fun areContentsTheSame(
    oldItem: MovieModel, newItem: MovieModel
  ): Boolean = oldItem == newItem
  
  override fun areItemsTheSame(
    oldItem: MovieModel, newItem: MovieModel
  ): Boolean = oldItem.id == newItem.id
}