package com.example.moviedocs.presentation.movielist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieItemHorizontalViewHolderBinding
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.presentation.base.BaseViewHolder
import com.example.moviedocs.utils.formatVoteAverage
import com.example.moviedocs.utils.loadImgFromUrl

class MovieListHorizontalAdapter :
  BaseListAdapter<MovieItemModel, MovieItemHorizontalViewHolderBinding>(
    MovieItemModelDiffCallBack
  ) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder<MovieItemModel, MovieItemHorizontalViewHolderBinding> =
    VH(
      MovieItemHorizontalViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  private inner class VH(
    binding: MovieItemHorizontalViewHolderBinding
  ) : BaseViewHolder<MovieItemModel, MovieItemHorizontalViewHolderBinding>(binding) {

    init {
      binding.root.setOnClickListener {
        val position: Int = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) onItemClickListener?.invoke(getItem(position))
      }
    }

    @SuppressLint("SetTextI18n")
    override fun bind(item: MovieItemModel) {
      binding.apply {
        movieItemImg.loadImgFromUrl(item.posterPath)
        movieItemTitle.text = item.title
        movieItemRatingVoteAverage.text = item.voteAverage.formatVoteAverage()
        movieItemRatingVoteCout.text = item.voteCount.toString()
      }
    }
  }
}