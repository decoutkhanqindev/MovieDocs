package com.example.moviedocs.presentation.media

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.BuildConfig
import com.example.moviedocs.databinding.MediaItemViewHolderBinding
import com.example.moviedocs.domain.model.moviedetail.image.MediaItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.presentation.base.BaseViewHolder
import com.example.moviedocs.utils.download.Downloader
import com.example.moviedocs.utils.dpToPx
import com.example.moviedocs.utils.loadImgFromUrl

class MediaListAdapter(
  private val type: MediaType,
  private val downloader: Downloader
) : BaseListAdapter<MediaItemModel, MediaItemViewHolderBinding>(MediaItemModelDiffCallback) {

  override fun onCreateViewHolder(
    parent: ViewGroup, viewType: Int
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

        val newWidth: Int = when (type) {
          MediaType.BACKDROP -> 250
          MediaType.LOGO -> 150
          MediaType.POSTER -> 100
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

        handleDownloadImg(item.filePath, type.name.lowercase())
      }
    }

    private fun handleDownloadImg(url: String, type: String) {
      binding.downloadBtn.setOnClickListener {
        downloader.downloadFile(
          url = BuildConfig.ORIGINAL_IMG_URL + url,
          filename = "$type.jpg",
          mimeType = "image/jpeg"
        )
      }
    }
  }

  enum class MediaType {
    BACKDROP, LOGO, POSTER
  }
}