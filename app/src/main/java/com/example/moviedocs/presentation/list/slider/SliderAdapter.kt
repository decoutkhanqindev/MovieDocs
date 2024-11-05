package com.example.moviedocs.presentation.list.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviedocs.databinding.SliderItemViewHolderBinding
import com.example.moviedocs.domain.model.MovieModel

class SliderAdapter(
  private val requestManager: RequestManager,
//  private val onItemClicked: (MovieModel) -> Unit
) : ListAdapter<MovieModel, SliderAdapter.SliderViewHolder>(MovieModelItemCallBack) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder =
    SliderViewHolder(
      SliderItemViewHolderBinding.inflate(
        /* inflater = */ LayoutInflater.from(parent.context),
        /* parent = */ parent,
        /* attachToParent = */ false
      )
    )
  
  override fun onBindViewHolder(holder: SliderViewHolder, position: Int) =
    holder.bind(getItem(position))
  
  inner class SliderViewHolder(
    private val binding: SliderItemViewHolderBinding
  ) : RecyclerView.ViewHolder(binding.root) {
    
    fun bind(item: MovieModel) {
      binding.run {
        val imageUrl = "https://image.tmdb.org/t/p/w500${item.posterPath}"
        requestManager.load(imageUrl)
          .fitCenter()
          .centerCrop()
          .transition(DrawableTransitionOptions.withCrossFade())
          .into(sliderItemImg)
        
//        sliderItemTitleText.text = item.title
      }
    }
  }
}



