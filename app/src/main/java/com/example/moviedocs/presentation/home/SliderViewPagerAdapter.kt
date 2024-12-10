package com.example.moviedocs.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviedocs.databinding.SliderItemViewHolderBinding
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.presentation.movielist.MovieItemModelDiffCallBack
import com.example.moviedocs.utils.loadImgFromUrl

class SliderViewPagerAdapter(
  private val viewPager: ViewPager2,
) : ListAdapter<MovieItemModel, SliderViewPagerAdapter.SliderViewHolder>(
  MovieItemModelDiffCallBack
) {

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
        viewPager.setCurrentItem(1, true)
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



