package com.example.moviedocs.presentation.home.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieItemViewHolderBinding
import com.example.moviedocs.domain.model.list.MovieItemModel
import com.example.moviedocs.presentation.home.MovieItemModelCallBack
import com.example.moviedocs.utils.loadImgFromUrl

class UpcomingAdapter(
//  private val onItemClick: () -> Unit
) : ListAdapter<MovieItemModel, UpcomingAdapter.UpComingViewHolder>(MovieItemModelCallBack) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingViewHolder =
    UpComingViewHolder(
      MovieItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  
  override fun onBindViewHolder(holder: UpComingViewHolder, position: Int) =
    holder.bind(getItem(position))
  
  inner class UpComingViewHolder(
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