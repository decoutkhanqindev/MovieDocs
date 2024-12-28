package com.example.moviedocs.presentation.media

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MediaItemViewHolderBinding
import com.example.moviedocs.domain.model.moviedetail.image.MediaItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.presentation.base.BaseViewHolder
import com.example.moviedocs.utils.loadImgFromUrl

class MediaListAdapter :
  BaseListAdapter<MediaItemModel, MediaItemViewHolderBinding>(MediaItemModelDiffCallback) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder<MediaItemModel, MediaItemViewHolderBinding> =
    VH(
      MediaItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  private inner class VH(
    binding: MediaItemViewHolderBinding
  ) : BaseViewHolder<MediaItemModel, MediaItemViewHolderBinding>(binding) {

    init {
      binding.root.setOnClickListener {
        val position: Int = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) onItemClickListener?.invoke(getItem(position))
      }
    }

    @SuppressLint("SetTextI18n")
    override fun bind(item: MediaItemModel) {
      binding.apply {
        val params: ConstraintLayout.LayoutParams =
          mediaItemImg.layoutParams as ConstraintLayout.LayoutParams
        params.dimensionRatio = "${item.aspectRatio}"
        mediaItemImg.loadImgFromUrl(item.filePath)
      }
    }
  }
}