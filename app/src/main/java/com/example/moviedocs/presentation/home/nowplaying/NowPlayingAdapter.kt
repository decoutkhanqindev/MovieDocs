package com.example.moviedocs.presentation.home.nowplaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieItemViewHolderBinding
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.presentation.home.MovieModelItemCallBack
import com.example.moviedocs.utils.loadImgFromUrl

class NowPlayingAdapter(
//  private val onMovieClick: (MovieModel) -> Unit
) : ListAdapter<MovieModel, NowPlayingAdapter.NowPlayingViewHolder>(MovieModelItemCallBack) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder =
    NowPlayingViewHolder(
      MovieItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  
  override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) =
    holder.bind(getItem(position))
  
  inner class NowPlayingViewHolder(
    private val binding: MovieItemViewHolderBinding
  ) : RecyclerView.ViewHolder(binding.root) {
    
    fun bind(item: MovieModel) {
      binding.apply {
        movieItemImg.loadImgFromUrl(item.posterPath)
        movieItemTitle.text = item.title
        movieItemRatingVoteAverage.text = String.format("%.1f", item.voteAverage)
        movieItemRatingVoteCout.text = item.voteCount.toString()
      }
    }
  }
}