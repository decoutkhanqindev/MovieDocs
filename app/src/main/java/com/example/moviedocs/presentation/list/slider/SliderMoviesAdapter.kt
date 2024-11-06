package com.example.moviedocs.presentation.list.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviedocs.databinding.SliderItemViewHolderBinding
import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.utils.loadImgFromUrl

class SliderMoviesAdapter(
  private val viewPager: ViewPager2,
//  private val onItemClicked: (MovieModel) -> Unit
) : ListAdapter<MovieModel, SliderMoviesAdapter.SliderViewHolder>(MovieModelItemCallBack) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder =
    SliderViewHolder(
      SliderItemViewHolderBinding.inflate(/* inflater = */ LayoutInflater.from(parent.context),/* parent = */
        parent,/* attachToParent = */
        false
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
    private val binding: SliderItemViewHolderBinding
  ) : RecyclerView.ViewHolder(binding.root) {
    
    fun bind(item: MovieModel) {
      binding.run {
        sliderItemImg.loadImgFromUrl(item.posterPath)
//        sliderItemTitleText.text = item.title
      }
    }
  }
}



