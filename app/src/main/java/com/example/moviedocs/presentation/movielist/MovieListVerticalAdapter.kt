package com.example.moviedocs.presentation.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieItemVerticalViewHolderBinding
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.utils.formatTimestamp
import com.example.moviedocs.utils.loadImgFromUrl

class MovieListVerticalAdapter :
  BaseListAdapter<MovieItemModel, MovieItemVerticalViewHolderBinding>(MovieItemModelDiffCallBack) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
    VH(
      MovieItemVerticalViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  
  private inner class VH(binding: MovieItemVerticalViewHolderBinding) : BaseViewHolder(binding) {
    
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
        movieItemRatingOverView.text = item.overview
        movieItemRatingReleaseDate.text = item.releaseDate.formatTimestamp()
      }
    }
  }
}

