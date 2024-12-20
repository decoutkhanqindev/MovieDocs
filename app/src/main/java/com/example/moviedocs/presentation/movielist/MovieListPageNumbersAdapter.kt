package com.example.moviedocs.presentation.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.PageNumbersItemViewHolderBinding
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.presentation.base.BaseViewHolder

class MovieListPageNumbersAdapter : BaseListAdapter<Int, PageNumbersItemViewHolderBinding>(
  PageDiffUtil
) {

  private var selectedPosition = 0

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder<Int, PageNumbersItemViewHolderBinding> =
    VH(
      PageNumbersItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  fun setCurrentPage(page: Int) {
    if (page >= 0 && page != selectedPosition) {
      val oldPosition: Int = selectedPosition
      selectedPosition = page
      notifyItemChanged(oldPosition)
      notifyItemChanged(selectedPosition)
    }
  }

  private inner class VH(
    binding: PageNumbersItemViewHolderBinding
  ) : BaseViewHolder<Int, PageNumbersItemViewHolderBinding>(binding) {

    init {
      binding.root.setOnClickListener {
        val position: Int = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) {
          setCurrentPage(position)
          onItemClickListener?.invoke(getItem(position))
        }
      }
    }

    override fun bind(item: Int) {
      binding.apply {
        pageNumber.text = "$item"
        root.apply {
          isSelected = selectedPosition == bindingAdapterPosition
          checkedIcon = null
        }
      }
    }
  }

  private object PageDiffUtil : DiffUtil.ItemCallback<Int>() {
    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem

    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
  }
}