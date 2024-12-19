package com.example.moviedocs.presentation.movielist.year

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieListItemByYearViewHolderBinding
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.presentation.movielist.MovieItemModelDiffCallBack

class MovieListItemByYearAdapter :
  BaseListAdapter<MovieItemModel, MovieListItemByYearViewHolderBinding>(
    MovieItemModelDiffCallBack
  ) {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseListAdapter<MovieItemModel, MovieListItemByYearViewHolderBinding>.BaseViewHolder =
    VH(
      MovieListItemByYearViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  private inner class VH(
    binding: MovieListItemByYearViewHolderBinding
  ) : BaseViewHolder(binding) {

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
        movieItemCharacter.text = "as ${item.character}"
      }
    }
  }
}