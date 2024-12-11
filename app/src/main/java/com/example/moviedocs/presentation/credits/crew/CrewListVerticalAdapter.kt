package com.example.moviedocs.presentation.credits.crew

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.CreditItemVerticalViewHolderBinding
import com.example.moviedocs.domain.model.credits.CrewItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.utils.loadImgFromUrl

class CrewListVerticalAdapter :
  BaseListAdapter<CrewItemModel, CreditItemVerticalViewHolderBinding>(
    CrewItemModelDiffCallBack
  ) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
    VH(
      CreditItemVerticalViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  private inner class VH(
    binding: CreditItemVerticalViewHolderBinding
  ) : BaseViewHolder(binding) {

    init {
      binding.root.setOnClickListener {
        val position: Int = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION)
          onItemClickListener?.invoke(getItem(position))
      }
    }

    @SuppressLint("SetTextI18n")
    override fun bind(item: CrewItemModel) {
      binding.apply {
        creditItemImg.loadImgFromUrl(item.profilePath)
        creditItemName.text = item.name
        creditItemGender.text = item.gender
        creditItemCharacter.text = item.job
        creditItemPopularity.text = item.popularity.toString()
      }
    }
  }
}