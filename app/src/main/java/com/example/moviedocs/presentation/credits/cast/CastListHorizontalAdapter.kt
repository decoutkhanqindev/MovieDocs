package com.example.moviedocs.presentation.credits.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.CreditItemHorizontalViewHolderBinding
import com.example.moviedocs.domain.model.credits.CastItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.utils.loadImgFromUrl

class CastListHorizontalAdapter :
  BaseListAdapter<CastItemModel, CreditItemHorizontalViewHolderBinding>(
    CastItemDiffCallBack
  ) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
    VH(
      CreditItemHorizontalViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  private inner class VH(
    binding: CreditItemHorizontalViewHolderBinding
  ) : BaseViewHolder(binding) {

    init {
      binding.root.setOnClickListener {
        val position: Int = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION)
          onItemClickListener?.invoke(getItem(position))
      }
    }

    override fun bind(item: CastItemModel) {
      binding.apply {
        creditItemImg.loadImgFromUrl(item.profilePath)
        creditItemName.text = item.name
      }
    }
  }

  private object CastItemDiffCallBack : DiffUtil.ItemCallback<CastItemModel>() {
    override fun areItemsTheSame(
      oldItem: CastItemModel,
      newItem: CastItemModel
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
      oldItem: CastItemModel,
      newItem: CastItemModel
    ): Boolean = oldItem == newItem
  }
}