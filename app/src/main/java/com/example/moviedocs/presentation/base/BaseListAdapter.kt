package com.example.moviedocs.presentation.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T : Any, VB : ViewBinding>(
  diffCallBack: DiffUtil.ItemCallback<T>,
) : ListAdapter<T, BaseViewHolder<T, VB>>(diffCallBack) {

  // init in child adapter
  internal var onItemClickListener: ((T) -> Unit)? = null

  override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
    holder.bind(getItem(position))
  }

  // activity or fragment call
  fun setOnItemClickListener(listener: (T) -> Unit) {
    onItemClickListener = listener
  }
}






