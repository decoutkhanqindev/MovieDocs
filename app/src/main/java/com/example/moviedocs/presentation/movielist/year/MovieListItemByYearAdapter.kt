package com.example.moviedocs.presentation.movielist.year

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieListItemByYearViewHolderBinding
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.presentation.base.BaseViewHolder
import com.example.moviedocs.presentation.movielist.MovieItemModelDiffCallBack
import com.example.moviedocs.utils.gone
import com.example.moviedocs.utils.visible

class MovieListItemByYearAdapter :
  BaseListAdapter<MovieItemModel, MovieListItemByYearViewHolderBinding>(
    MovieItemModelDiffCallBack
  ) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder<MovieItemModel, MovieListItemByYearViewHolderBinding> =
    VH(
      MovieListItemByYearViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  private inner class VH(
    binding: MovieListItemByYearViewHolderBinding
  ) : BaseViewHolder<MovieItemModel, MovieListItemByYearViewHolderBinding>(binding) {

    init {
      binding.root.setOnClickListener {
        val position: Int = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) onItemClickListener?.invoke(getItem(position))
      }
    }

    @SuppressLint("SetTextI18n")
    override fun bind(item: MovieItemModel) {
      binding.apply {
        movieItemName.text = item.title
        if (item.character == "Unknown character") {
          movieItemCharacter.gone()
          movieItemDepartment.visible()
          movieItemDepartment.text = "as ${item.department}"
        } else {
          movieItemCharacter.visible()
          movieItemDepartment.gone()
          movieItemCharacter.text = "as ${item.character}"
        }
      }
    }
  }
}