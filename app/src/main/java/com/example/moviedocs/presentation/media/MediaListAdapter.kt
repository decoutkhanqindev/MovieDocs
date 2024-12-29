package com.example.moviedocs.presentation.media

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MediaItemViewHolderBinding
import com.example.moviedocs.di.ImgUrl
import com.example.moviedocs.domain.model.moviedetail.image.MediaItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.presentation.base.BaseViewHolder
import com.example.moviedocs.utils.dpToPx
import com.example.moviedocs.utils.loadImgFromUrl
import javax.inject.Inject

class MediaListAdapter(
  private val type: MediaType,
) : BaseListAdapter<MediaItemModel, MediaItemViewHolderBinding>(MediaItemModelDiffCallback) {

  override fun onCreateViewHolder(
    parent: ViewGroup, viewType: Int
  ): BaseViewHolder<MediaItemModel, MediaItemViewHolderBinding> = VH(
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
        val newWidth: Int = when (type) {
          MediaType.BACKDROPS -> 250
          MediaType.LOGOS -> 150
          MediaType.POSTERS -> 100
        }.dpToPx(itemView.context)

        constraintLayout.updateLayoutParams {
          width = newWidth
        }

        ConstraintSet().apply {
          clone(constraintLayout)
          constrainWidth(constraintLayout.id, newWidth)
          setDimensionRatio(mediaItemImg.id, "${item.aspectRatio}")
          applyTo(constraintLayout)
        }

        mediaItemImg.loadImgFromUrl(item.filePath)
      }
    }
  }

  enum class MediaType {
    BACKDROPS, LOGOS, POSTERS
  }
}