package com.example.moviedocs.presentation.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.moviedocs.databinding.FragmentMovieListBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.list.slider.SliderAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class MovieListFragment :
  BaseFragment<FragmentMovieListBinding>(FragmentMovieListBinding::inflate) {
  
  private val viewModel: MovieListViewModel by viewModels()
  
  private lateinit var viewPager: ViewPager2
  
  private val sliderHandler: Handler by lazy {
    Handler(Looper.getMainLooper())
  }
  
  private val sliderRunnable: Runnable by lazy {
    Runnable {
      viewPager.currentItem += 1
    }
  }
  
  private val sliderAdapter: SliderAdapter by lazy {
    SliderAdapter(
      viewPager = viewPager,
      requestManager = Glide.with(this)
    )
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setUpSliderImg()
    bindViewModel()
  }
  
  private fun setUpSliderImg() {
    viewPager = binding.sliderImg
    viewPager.apply {
      adapter = sliderAdapter
      offscreenPageLimit = 3
      clipToPadding = false
      clipChildren = false
      getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }
    setUpSliderTransformer()
  }
  
  private fun setUpSliderTransformer() {
    val transformer = CompositePageTransformer()
    transformer.apply {
      addTransformer(MarginPageTransformer(10))
      addTransformer { page: View, position: Float ->
        val r: Float = 1 - abs(position)
        page.scaleY = 0.85f + r * 0.15f
      }
    }
    
    viewPager.apply {
      setPageTransformer(transformer)
      currentItem = 1
      registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
          super.onPageSelected(position)
          sliderHandler.removeCallbacks(sliderRunnable)
          sliderHandler.postDelayed(sliderRunnable, 2000)
        }
      })
    }
  }
  
  override fun onPause() {
    super.onPause()
    sliderHandler.removeCallbacks(sliderRunnable)
  }
  
  override fun onResume() {
    super.onResume()
    sliderHandler.postDelayed(sliderRunnable, 2000)
  }
  
  private fun bindViewModel() {
    viewLifecycleOwner.lifecycleScope.launch {
      viewModel.movieListUiStateFlow.collect { it: MovieListUiState ->
        when (it) {
          is MovieListUiState.FirstPageLoading -> {
            binding.sliderProgressBar.visibility = View.VISIBLE
            binding.sliderImg.visibility = View.GONE
            sliderAdapter.submitList(emptyList())
          }
          
          is MovieListUiState.FirstPageError -> {
            binding.sliderProgressBar.visibility = View.VISIBLE
            binding.sliderImg.visibility = View.GONE
            sliderAdapter.submitList(emptyList())
            
          }
          
          is MovieListUiState.Success -> {
            binding.sliderProgressBar.visibility = View.GONE
            binding.sliderImg.visibility = View.VISIBLE
            sliderAdapter.submitList(it.items)
          }
        }
      }
    }
  }
}