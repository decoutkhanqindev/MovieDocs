package com.example.moviedocs.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieItemHorizontalViewHolderBinding
import com.example.moviedocs.domain.model.list.MovieItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.utils.loadImgFromUrl

class MovieListHorizontalAdapter :
  BaseListAdapter<MovieItemModel, MovieItemHorizontalViewHolderBinding>(MovieItemModelDiffCallBack) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
    MovieListVerticalViewHolder(
      MovieItemHorizontalViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  
  private inner class MovieListVerticalViewHolder(
    binding: MovieItemHorizontalViewHolderBinding,
  ) : BaseViewHolder(binding) {
    
    init {
      binding.root.setOnClickListener {
        val position = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) onItemClickListener?.invoke(getItem(position))
      }
    }
    
    override fun bind(item: MovieItemModel) {
      binding.apply {
        movieItemImg.loadImgFromUrl(item.posterPath)
        movieItemTitle.text = item.title
        movieItemRatingVoteAverage.text = "%.1f".format(item.voteAverage)
        movieItemRatingVoteCout.text = "${item.voteCount}"
      }
    }
  }
}