package com.example.moviedocs.presentation.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T : Any, VB : ViewBinding>(
  diffCallBack: DiffUtil.ItemCallback<T>,
) : ListAdapter<T, BaseListAdapter<T, VB>.BaseViewHolder>(diffCallBack) {
  
  private var onItemClickListener: ((T) -> Unit)? = null
  
  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    getItem(position)?.let { item: T ->
      holder.bind(item) // bind item with view holder
      
      // bind item click listener
      holder.itemView.setOnClickListener {
        onItemClickListener?.invoke(item)
      }
    }
  }
  
  fun setOnItemClickListener(listener: (T) -> Unit) {
    onItemClickListener = listener
  }
  
  abstract inner class BaseViewHolder(
    protected val binding: VB,
  ) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T)
  }
}




