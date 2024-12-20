package com.example.moviedocs.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviedocs.databinding.SliderItemViewHolderBinding
import com.example.moviedocs.domain.model.slider.SliderImageItemModel
import com.example.moviedocs.utils.loadImgFromDrawable

class SliderViewPagerAdapter(
  private val viewPager: ViewPager2,
) : ListAdapter<SliderImageItemModel, SliderViewPagerAdapter.SliderViewHolder>(
  SliderImgItemModelDiffCallBack
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder =
    SliderViewHolder(
      SliderItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
    holder.bind(getItem(position % itemCount))
    if (position == itemCount - 1) {
      viewPager.setCurrentItem(0, true)
    }

    // position = 0 1 2
    // itemCount = 3

    // currentItem = position % itemCount = 0 % 3 = 0
    // currentItem = position % itemCount = 1 % 3 = 1
    // currentItem = position % itemCount = 2 % 3 = 2

    // position = 2 -> currentItem = 0
  }

  inner class SliderViewHolder(
    private val binding: SliderItemViewHolderBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SliderImageItemModel) {
      binding.run {
        sliderItemImg.loadImgFromDrawable(item.drawableId)
      }
    }
  }
}



