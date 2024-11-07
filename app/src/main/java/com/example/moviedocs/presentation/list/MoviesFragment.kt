package com.example.moviedocs.presentation.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.moviedocs.R
import com.example.moviedocs.databinding.FragmentMoviesBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.list.nowplaying.NowPlayingAdapter
import com.example.moviedocs.presentation.list.nowplaying.NowPlayingViewModel
import com.example.moviedocs.presentation.list.popular.PopularAdapter
import com.example.moviedocs.presentation.list.popular.PopularViewModel
import com.example.moviedocs.presentation.list.slider.SliderMoviesAdapter
import com.example.moviedocs.presentation.list.upcoming.UpcomingViewModel
import com.example.moviedocs.utils.gone
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.navigateTo
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {
  
  private val nowPlayingViewModel: NowPlayingViewModel by viewModels()
  
  private val popularViewModel: PopularViewModel by viewModels()
  
  private val upComingViewModel: UpcomingViewModel by viewModels()
  
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
  
  private val nowPlayingAdapter: NowPlayingAdapter by lazy {
    NowPlayingAdapter()
  }
  
  private val popularAdapter: PopularAdapter by lazy {
    PopularAdapter()
  }
  
  private val upComingAdapter: PopularAdapter by lazy {
    PopularAdapter()
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setUpSliderImg()
    setUpNavigate()
    setUpRecyclerView(binding.nowPlayingRecycleView, nowPlayingAdapter)
    setUpRecyclerView(binding.popularRecycleView, popularAdapter)
    setUpRecyclerView(binding.upcomingRecycleView, upComingAdapter)
    bindViewModel()
  }
  
  private fun setUpSliderImg() {
    viewPager = binding.sliderViewPager
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
  
  private fun setUpNavigate() {
    binding.searchBtn.navigateTo(R.id.action_moviesFragment_to_searchMoviesFragment)
  }
  
  private fun setUpRecyclerView(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder> // Accepts any subtype of Adapter
  ) {
    recyclerView.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.HORIZONTAL, false
      )
      this.adapter = adapter
    }
  }
  
  private fun bindViewModel() {
    launchAndRepeatStarted(
      { nowPlayingViewModel.moviesUiStateFlow.collect(::renderNowPlayingUiState) },
      { popularViewModel.moviesUiStateFlow.collect(::renderPopularUiState) },
      { upComingViewModel.moviesUiStateFlow.collect(::renderUpComingUiState)}
    )
  }
  
  private fun renderNowPlayingUiState(state: MoviesUiState) {
    when (state) {
      MoviesUiState.FirstPageLoading -> {
        binding.apply {
          sliderProgressBar.visible()
          sliderViewPager.gone()
          nowPlayingProgressBar.visible()
          nowPlayingRecycleView.invisible()
        }
        sliderAdapter.submitList(emptyList())
        nowPlayingAdapter.submitList(emptyList())
      }
      
      MoviesUiState.FirstPageError -> {
        binding.apply {
          sliderProgressBar.visible()
          sliderViewPager.gone()
          nowPlayingProgressBar.visible()
          nowPlayingRecycleView.invisible()
        }
        sliderAdapter.submitList(emptyList())
        nowPlayingAdapter.submitList(emptyList())
      }
      
      is MoviesUiState.Success -> {
        binding.apply {
          sliderProgressBar.gone()
          sliderViewPager.visible()
          nowPlayingProgressBar.gone()
          nowPlayingRecycleView.visible()
        }
        sliderAdapter.submitList(state.items)
        nowPlayingAdapter.submitList(state.items)
      }
    }
  }
  
  private fun renderPopularUiState(state: MoviesUiState) {
    when (state) {
      MoviesUiState.FirstPageLoading -> {
        binding.apply {
          popularProgressBar.visible()
          popularRecycleView.invisible()
        }
        popularAdapter.submitList(emptyList())
      }
      
      MoviesUiState.FirstPageError -> {
        binding.apply {
          popularProgressBar.visible()
          popularRecycleView.invisible()
        }
        popularAdapter.submitList(emptyList())
      }
      
      is MoviesUiState.Success -> {
        binding.apply {
          popularProgressBar.gone()
          popularRecycleView.visible()
        }
        popularAdapter.submitList(state.items)
      }
    }
  }
  
  private fun renderUpComingUiState(state: MoviesUiState) {
    when (state) {
      MoviesUiState.FirstPageLoading -> {
        binding.apply {
          upcomingProgressBar.visible()
          upcomingRecycleView.invisible()
        }
        upComingAdapter.submitList(emptyList())
      }
      
      MoviesUiState.FirstPageError -> {
        binding.apply {
          upcomingProgressBar.visible()
          upcomingRecycleView.invisible()
        }
        upComingAdapter.submitList(emptyList())
      }
      
      is MoviesUiState.Success -> {
        binding.apply {
          upcomingProgressBar.gone()
          upcomingRecycleView.visible()
        }
        upComingAdapter.submitList(state.items)
      }
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
  
  override fun onDestroyView() {
    // avoid memory leak
    binding.apply {
      sliderViewPager.adapter = null
      nowPlayingRecycleView.adapter = null
    }
    super.onDestroyView()
  }
  
  companion object {
    private const val VISIBLE_THRESHOLD = 2
  }
}