package com.example.moviedocs.presentation.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T : Any, VB : ViewBinding>(
  internal val binding: VB,
) : RecyclerView.ViewHolder(binding.root) {
  internal abstract fun bind(item: T)
}