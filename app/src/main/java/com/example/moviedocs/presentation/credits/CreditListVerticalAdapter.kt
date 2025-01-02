package com.example.moviedocs.presentation.credits

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.CreditItemVerticalViewHolderBinding
import com.example.moviedocs.domain.model.credits.CreditItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.presentation.base.BaseViewHolder
import com.example.moviedocs.utils.loadImgFromUrl

class CreditListVerticalAdapter :
  BaseListAdapter<CreditItemModel, CreditItemVerticalViewHolderBinding>(
    CreditItemModelDiffCallBack
  ) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder<CreditItemModel, CreditItemVerticalViewHolderBinding> =
    VH(
      CreditItemVerticalViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  private inner class VH(
    binding: CreditItemVerticalViewHolderBinding
  ) : BaseViewHolder<CreditItemModel, CreditItemVerticalViewHolderBinding>(binding) {

    init {
      binding.root.setOnClickListener {
        val position: Int = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION)
          onItemClickListener?.invoke(getItem(position))
      }
    }

    @SuppressLint("SetTextI18n")
    override fun bind(item: CreditItemModel) {
      binding.apply {
        creditItemImg.loadImgFromUrl(item.profilePath)
        creditItemName.text = item.name
        creditItemGender.text = item.gender
        creditItemCharacter.text =
          if (item.character == "Unknown character") {
            item.department
          } else {
            item.character
          }
        creditItemPopularity.text = item.popularity.toString()
      }
    }
  }
}