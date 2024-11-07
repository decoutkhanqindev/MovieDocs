package com.example.moviedocs.presentation.list.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieItemViewHolderBinding
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.presentation.list.MovieModelItemCallBack
import com.example.moviedocs.utils.loadImgFromUrl

class PopularAdapter(
//  private val onItemClick: () -> Unit
) : ListAdapter<MovieModel, PopularAdapter.PopularViewHolder>(MovieModelItemCallBack) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder =
    PopularViewHolder(
      MovieItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  
  override fun onBindViewHolder(holder: PopularViewHolder, position: Int) =
    holder.bind(getItem(position))
  
  inner class PopularViewHolder(
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