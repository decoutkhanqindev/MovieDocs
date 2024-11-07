package com.example.moviedocs.presentation.list.nowplaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieItemViewHolderBinding
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.presentation.list.MovieModelItemCallBack
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
        movieItemTitleText.text = item.title
      }
    }
  }
}