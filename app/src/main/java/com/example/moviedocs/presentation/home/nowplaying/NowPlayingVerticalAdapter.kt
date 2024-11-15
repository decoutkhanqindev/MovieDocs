package com.example.moviedocs.presentation.home.nowplaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieItemVerticalViewHolderBinding
import com.example.moviedocs.domain.model.list.MovieItemModel
import com.example.moviedocs.presentation.home.MovieItemModelCallBack
import com.example.moviedocs.utils.loadImgFromUrl

class NowPlayingVerticalAdapter(
//  private val onMovieClick: (MovieModel) -> Unit
) : ListAdapter<MovieItemModel, NowPlayingVerticalAdapter.NowPlayingViewHolder>(
  MovieItemModelCallBack
) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder =
    NowPlayingViewHolder(
      MovieItemVerticalViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  
  override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) =
    holder.bind(getItem(position))
  
  inner class NowPlayingViewHolder(
    private val binding: MovieItemVerticalViewHolderBinding,
  ) : RecyclerView.ViewHolder(binding.root) {
    
    fun bind(item: MovieItemModel) {
      binding.apply {
        movieItemImg.loadImgFromUrl(item.posterPath)
        movieItemTitle.text = item.title
        movieItemRatingVoteAverage.text = String.format("%.1f", item.voteAverage)
        movieItemRatingOverView.text = item.overview
      }
    }
  }
}
