package com.example.moviedocs.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.moviedocs.databinding.SliderItemViewHolderBinding
import com.example.moviedocs.domain.model.slider.SliderImageItemModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.presentation.base.BaseViewHolder
import com.example.moviedocs.utils.loadImgFromDrawable

class SliderViewPagerAdapter(
  private val viewPager: ViewPager2,
) : BaseListAdapter<SliderImageItemModel, SliderItemViewHolderBinding>(
  SliderImgItemModelDiffCallBack
) {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder<SliderImageItemModel, SliderItemViewHolderBinding> =
    VH(
      SliderItemViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  override fun onBindViewHolder(
    holder: BaseViewHolder<SliderImageItemModel, SliderItemViewHolderBinding>,
    position: Int
  ) {
    holder.bind(getItem(position % 3)) // 3 is itemCount
    if (position == currentList.lastIndex) {
      viewPager.setCurrentItem(0, true)
    }
  }

  private inner class VH(
    binding: SliderItemViewHolderBinding
  ) : BaseViewHolder<SliderImageItemModel, SliderItemViewHolderBinding>(binding) {
    override fun bind(item: SliderImageItemModel) {
      binding.sliderItemImg.loadImgFromDrawable(item.drawableId)
    }
  }
}



