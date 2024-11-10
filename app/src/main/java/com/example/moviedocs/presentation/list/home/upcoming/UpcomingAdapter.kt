package com.example.moviedocs.presentation.list.home.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieItemViewHolderBinding
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.presentation.list.MovieModelItemCallBack
import com.example.moviedocs.utils.loadImgFromUrl

class UpcomingAdapter(
//  private val onItemClick: () -> Unit
) : ListAdapter<MovieModel, UpcomingAdapter.UpComingViewHolder>(MovieModelItemCallBack) {
  
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
    
    fun bind(item: MovieModel) {
      binding.apply {
        movieItemImg.loadImgFromUrl(item.posterPath)
        movieItemTitle.text = item.title
      }
    }
  }
}