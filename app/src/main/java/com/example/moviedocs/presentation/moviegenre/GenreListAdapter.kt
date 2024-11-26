package com.example.moviedocs.presentation.moviegenre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.GenreItemViewHolderBinding
import com.example.moviedocs.domain.model.moviegenre.GenreItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter

class GenreListAdapter :
  BaseListAdapter<GenreItemModel, GenreItemViewHolderBinding>(GenreItemModelDiffCallBack) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
    VH(
      GenreItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  
  private inner class VH(binding: GenreItemViewHolderBinding) : BaseViewHolder(binding) {
    
    init {
      binding.root.setOnClickListener {
        val position = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) onItemClickListener?.invoke(getItem(position))
      }
    }
    
    override fun bind(item: GenreItemModel) {
      binding.genreItemName.text = item.name
    }
  }
}