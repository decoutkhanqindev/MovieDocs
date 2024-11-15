package com.example.moviedocs.presentation.home.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieItemHorizontalViewHolderBinding
import com.example.moviedocs.domain.model.list.MovieItemModel
import com.example.moviedocs.presentation.home.MovieItemModelCallBack
import com.example.moviedocs.utils.loadImgFromUrl

class PopularHorizontalAdapter(
//  private val onItemClick: () -> Unit
) :
  ListAdapter<MovieItemModel, PopularHorizontalAdapter.PopularViewHolder>(MovieItemModelCallBack) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder =
    PopularViewHolder(
      MovieItemHorizontalViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  
  override fun onBindViewHolder(holder: PopularViewHolder, position: Int) =
    holder.bind(getItem(position))
  
  inner class PopularViewHolder(
    private val binding: MovieItemHorizontalViewHolderBinding,
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