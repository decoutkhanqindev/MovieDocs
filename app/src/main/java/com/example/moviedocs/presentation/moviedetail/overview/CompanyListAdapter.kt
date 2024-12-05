package com.example.moviedocs.presentation.moviedetail.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.CompanyItemViewHolderBinding
import com.example.moviedocs.domain.model.moviedetail.company.CompanyItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.utils.loadImgFromUrl

class CompanyListAdapter : BaseListAdapter<CompanyItemModel, CompanyItemViewHolderBinding>(
  CompanyItemModelDiffCallback
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
    VH(
    CompanyItemViewHolderBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  inner class VH(
    binding: CompanyItemViewHolderBinding,
  ) : BaseViewHolder(binding) {

    init {
      binding.root.setOnClickListener {
        val position: Int = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) onItemClickListener?.invoke(getItem(position))
      }
    }

    override fun bind(item: CompanyItemModel) {
      binding.apply {
        companyItemTitle.text = item.name
        companyItemImg.loadImgFromUrl(item.logoPath)
      }
    }
  }

  private object CompanyItemModelDiffCallback : DiffUtil.ItemCallback<CompanyItemModel>() {
    override fun areContentsTheSame(
      oldItem: CompanyItemModel,
      newItem: CompanyItemModel,
    ): Boolean = oldItem == newItem

    override fun areItemsTheSame(
      oldItem: CompanyItemModel,
      newItem: CompanyItemModel,
    ): Boolean = oldItem.id == newItem.id
  }
}