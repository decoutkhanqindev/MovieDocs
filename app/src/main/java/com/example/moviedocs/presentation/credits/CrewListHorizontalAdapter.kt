package com.example.moviedocs.presentation.credits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.CreditItemHorizontalViewHolderBinding
import com.example.moviedocs.domain.model.credits.CrewItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.utils.loadImgFromUrl

class CrewListHorizontalAdapter :
  BaseListAdapter<CrewItemModel, CreditItemHorizontalViewHolderBinding>(
    CrewItemDiffCallBack
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

    override fun bind(item: CrewItemModel) {
      binding.apply {
        creditItemImg.loadImgFromUrl(item.profilePath)
        creditItemName.text = item.name
      }
    }
  }
}