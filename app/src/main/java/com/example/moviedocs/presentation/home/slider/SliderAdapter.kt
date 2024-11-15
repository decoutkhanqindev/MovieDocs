package com.example.moviedocs.presentation.home.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviedocs.databinding.SliderItemViewHolderBinding
import com.example.moviedocs.domain.model.list.MovieItemModel
import com.example.moviedocs.presentation.home.MovieItemModelCallBack
import com.example.moviedocs.utils.loadImgFromUrl

class SliderAdapter(
  private val viewPager: ViewPager2,
//  private val onItemClicked: (MovieModel) -> Unit
) : ListAdapter<MovieItemModel, SliderAdapter.SliderViewHolder>(MovieItemModelCallBack) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder =
    SliderViewHolder(
      SliderItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  
  override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
    holder.bind(getItem(position))
    // infinite scrolling
    if (position == currentList.lastIndex) {
      viewPager.post {
        viewPager.setCurrentItem(0, true)
      }
    }
  }
  
  inner class SliderViewHolder(
    private val binding: SliderItemViewHolderBinding,
  ) : RecyclerView.ViewHolder(binding.root) {
    
    fun bind(item: MovieItemModel) {
      binding.run {
        sliderItemImg.loadImgFromUrl(item.posterPath)
//        sliderItemTitleText.text = item.title
      }
    }
  }
}



