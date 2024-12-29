package com.example.moviedocs.presentation.credits.cast

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.CreditItemVerticalViewHolderBinding
import com.example.moviedocs.domain.model.credits.CastItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.presentation.base.BaseViewHolder
import com.example.moviedocs.utils.loadImgFromUrl
import javax.inject.Inject

class CastListVerticalAdapter :
  BaseListAdapter<CastItemModel, CreditItemVerticalViewHolderBinding>(
    CastItemModelDiffCallBack
  ) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder<CastItemModel, CreditItemVerticalViewHolderBinding> =
    VH(
      CreditItemVerticalViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  private inner class VH(
    binding: CreditItemVerticalViewHolderBinding
  ) : BaseViewHolder<CastItemModel, CreditItemVerticalViewHolderBinding>(binding) {

    init {
      binding.root.setOnClickListener {
        val position: Int = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION)
          onItemClickListener?.invoke(getItem(position))
      }
    }

    @SuppressLint("SetTextI18n")
    override fun bind(item: CastItemModel) {
      binding.apply {
        creditItemImg.loadImgFromUrl(item.profilePath)
        creditItemName.text = item.name
        creditItemGender.text = item.gender
        creditItemCharacter.text = item.character
        creditItemPopularity.text = item.popularity.toString()
      }
    }
  }
}