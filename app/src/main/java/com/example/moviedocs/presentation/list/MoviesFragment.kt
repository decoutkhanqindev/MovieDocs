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
import com.example.moviedocs.R
import com.example.moviedocs.databinding.FragmentMoviesBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.list.slider.SliderMoviesAdapter
import com.example.moviedocs.presentation.list.viewmodel.NowPlayingViewModel
import com.example.moviedocs.utils.gone
import com.example.moviedocs.utils.navigateTo
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {
  
  private val viewModel: NowPlayingViewModel by viewModels()
  
  private lateinit var viewPager: ViewPager2
  
  private val sliderHandler: Handler by lazy {
    Handler(Looper.getMainLooper())
  }
  
  private val sliderRunnable: Runnable by lazy {
    Runnable { viewPager.currentItem += 1 }
  }
  
  private val sliderAdapter: SliderMoviesAdapter by lazy {
    SliderMoviesAdapter(viewPager = viewPager)
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setUpSliderImg()
    setUpNavigate()
    bindViewModel()
  }
  
  private fun setUpSliderImg() {
    viewPager = binding.sliderImg
    viewPager.apply {
      adapter = sliderAdapter
      // sets the number of pages that should be kept in memory off-screen on either side of the current page.
      offscreenPageLimit = 3
      // This disables clipping of child views to the padding of the ViewPager2.
      clipToPadding = false
      // This disables clipping of child views to the bounds of the ViewPager2.
      clipChildren = false
      
      // This line accesses the first child of the ViewPager2 (which is typically a RecyclerView)
      // and sets its over-scroll mode to OVER_SCROLL_NEVER.
      getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }
    setUpSliderTransformer()
  }
  
  private fun setUpSliderTransformer() {
    // CompositePageTransformer is created to combine multiple page transformation effects. This allows you
    // to apply multiple transformations to the pages of the ViewPager2.
    val transformer = CompositePageTransformer()
    transformer.apply {
      addTransformer(MarginPageTransformer(20)) // This transformer adds a margin between pages
      
      //  This transformer modifies the scale of the pages based on their position. The position parameter
      //  represents the page's position relative to the center of the screen. The code calculates
      //  a scale factor (scaleY) that ranges from 0.85 to 1.0, creating a zoom-in effect as the
      //  page approaches the center.
      addTransformer { page: View, position: Float ->
        val r: Float = 1 - abs(position)
        page.scaleY = 0.85f + r * 0.15f
      }
    }
    
    viewPager.apply {
      setPageTransformer(transformer)
      currentItem = 1
      
      // A page change callback is registered to listen for page selection events. This callback is
      // triggered whenever the current page of the ViewPager2 changes.
      registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
          super.onPageSelected(position)
          sliderHandler.removeCallbacks(sliderRunnable)
          sliderHandler.postDelayed(sliderRunnable, 2000)
          
          // This method is called when a new page is selected. It removes any pending callbacks for
          // the sliderRunnable (likely a runnable for auto-scrolling) and then posts a delayed execution
          // of the sliderRunnable with a delay of 2000 milliseconds (2 seconds). This likely restarts
          // the auto-scrolling timer whenever a new page is selected.
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
            binding.sliderProgressBar.visible()
            binding.sliderImg.gone()
            sliderAdapter.submitList(emptyList())
          }
          
          is MovieListUiState.FirstPageError -> {
            binding.sliderProgressBar.visible()
            binding.sliderImg.gone()
            sliderAdapter.submitList(emptyList())
          }
          
          is MovieListUiState.Success -> {
            binding.sliderProgressBar.gone()
            binding.sliderImg.visible()
            sliderAdapter.submitList(it.items)
          }
        }
      }
    }
  }
  
  private fun setUpNavigate() {
    binding.searchBtn.navigateTo(R.id.action_moviesFragment_to_searchMoviesFragment)
  }
}