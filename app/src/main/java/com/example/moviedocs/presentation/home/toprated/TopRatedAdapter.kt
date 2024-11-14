package com.example.moviedocs.presentation.home.toprated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieItemViewHolderBinding
import com.example.moviedocs.domain.model.list.MovieItemModel
import com.example.moviedocs.presentation.home.MovieItemModelCallBack
import com.example.moviedocs.utils.loadImgFromUrl

class TopRatedAdapter(
//  private val onItemClick: (MovieModel) -> Unit
) : ListAdapter<MovieItemModel, TopRatedAdapter.TopRatedViewHolder>(MovieItemModelCallBack) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder =
    TopRatedViewHolder(
      MovieItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  
  override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) =
    holder.bind(getItem(position))
  
  inner class TopRatedViewHolder(
    private val binding: MovieItemViewHolderBinding
  ) : RecyclerView.ViewHolder(binding.root) {
    
    fun bind(item: MovieItemModel) {
      binding.apply {
        movieItemImg.loadImgFromUrl(item.posterPath)
        movieItemTitle.text = item.title
        movieItemRatingVoteAverage.text = String.format("%.1f", item.voteAverage)
        movieItemRatingVoteCout.text = item.voteCount.toString()
      }
    }
  }
}