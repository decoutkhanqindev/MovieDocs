package com.example.moviedocs.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.PageNumbersItemViewHolderBinding
import com.example.moviedocs.presentation.base.BaseListAdapter

class MovieListPageNumbersAdapter :
  BaseListAdapter<Int, PageNumbersItemViewHolderBinding>(PageDiffUtil()) {
  
  private var selectedPosition = 0
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
    PageNumbersItemViewHolder(
      PageNumbersItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  
  inner class PageNumbersItemViewHolder(
    binding: PageNumbersItemViewHolderBinding,
  ) : BaseViewHolder(binding) {
    
    init {
      binding.root.setOnClickListener {
        val position = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION && position != selectedPosition) {
          val oldPosition = selectedPosition
          selectedPosition = position
          notifyItemChanged(oldPosition)
          notifyItemChanged(selectedPosition)
          onItemClickListener?.invoke(getItem(position))
        }
      }
    }
    
    override fun bind(item: Int) {
      binding.apply {
        pageNumber.text = "$item"
        root.apply {
          isSelected = selectedPosition == bindingAdapterPosition
          checkedIcon = null
        }
      }
    }
  }
  
  private class PageDiffUtil : DiffUtil.ItemCallback<Int>() {
    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
    
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
  }
}