package com.example.moviedocs.presentation.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.moviedocs.databinding.PageNumbersItemViewHolderBinding
import com.example.moviedocs.presentation.base.BaseListAdapter

class MovieListPageNumbersAdapter :
  BaseListAdapter<Int, PageNumbersItemViewHolderBinding>(PageDiffUtil()) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
    PageNumbersItemViewHolder(
      PageNumbersItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  
  inner class PageNumbersItemViewHolder(
    binding: PageNumbersItemViewHolderBinding,
  ) : BaseViewHolder(binding) {
    
    @SuppressLint("SetTextI18n")
    override fun bind(item: Int) {
      binding.pageNumber.text = item.toString()
    }
  }
  
  private class PageDiffUtil : DiffUtil.ItemCallback<Int>() {
    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
    
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
  }
}