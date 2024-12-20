package com.example.moviedocs.presentation.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.GenreItemViewHolderBinding
import com.example.moviedocs.domain.model.genre.GenreItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.presentation.base.BaseViewHolder

class GenreListAdapter :
  BaseListAdapter<GenreItemModel, GenreItemViewHolderBinding>(GenreItemModelDiffCallBack) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder<GenreItemModel, GenreItemViewHolderBinding> =
    VH(
      GenreItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  private inner class VH(
    binding: GenreItemViewHolderBinding,
  ) : BaseViewHolder<GenreItemModel, GenreItemViewHolderBinding>(binding) {

    init {
      binding.root.setOnClickListener {
        val position: Int = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) onItemClickListener?.invoke(getItem(position))
      }
    }

    override fun bind(item: GenreItemModel) {
      binding.genreItemName.text = item.name
    }
  }

  private object GenreItemModelDiffCallBack : DiffUtil.ItemCallback<GenreItemModel>() {
    override fun areContentsTheSame(
      oldItem: GenreItemModel, newItem: GenreItemModel
    ): Boolean = oldItem == newItem

    override fun areItemsTheSame(
      oldItem: GenreItemModel, newItem: GenreItemModel
    ): Boolean = oldItem.id == newItem.id
  }
}