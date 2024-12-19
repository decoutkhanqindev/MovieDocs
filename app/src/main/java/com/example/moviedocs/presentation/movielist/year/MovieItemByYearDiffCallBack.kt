package com.example.moviedocs.presentation.movielist.year

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.moviedocs.domain.model.movielist.year.MovieListByYearModel

object MovieItemByYearDiffCallBack : DiffUtil.ItemCallback<MovieListByYearModel>() {
  @SuppressLint("DiffUtilEquals")
  override fun areContentsTheSame(
    oldItem: MovieListByYearModel, newItem: MovieListByYearModel,
  ): Boolean = oldItem.movies == newItem.movies

  override fun areItemsTheSame(
    oldItem: MovieListByYearModel, newItem: MovieListByYearModel,
  ): Boolean = oldItem.year == newItem.year
}