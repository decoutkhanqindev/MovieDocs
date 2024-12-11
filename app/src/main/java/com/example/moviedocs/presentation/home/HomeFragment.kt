package com.example.moviedocs.presentation.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.moviedocs.R
import com.example.moviedocs.databinding.FragmentHomeBinding
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.moviegenre.GenreListAdapter
import com.example.moviedocs.presentation.movielist.MovieListHorizontalAdapter
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.navigateTo
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

  private val viewModel: HomeViewModel by viewModels()

  private lateinit var viewPager: ViewPager2

  // Handler is used for scheduling tasks and delivering messages or Runnables to be executed on
  // specific thread (usually the main thread)
  // - It's associated with a thread's message queue (Looper).
  // - You can post Runnables or messages to the Handler, which will then be added to the message queue.
  // - The thread associated with the Handler processes messages and Runnables from the queue sequentially.
  // In case, this Handler is created with the main looper, meaning it's associated with the main thread.
  private val sliderHandler: Handler by lazy(LazyThreadSafetyMode.NONE) {
    Handler(Looper.getMainLooper())
  }

  // Runnable represents a task or a unit of work that can be executed with run()
  // - You create a Runnable object and define the task within its run() method.
  // - You can then pass this Runnable to a Handler to be executed on a specific thread.
  // In case, this Runnable contains the code to transfer the ViewPager2 to the next item
  // (viewPager.currentItem += 1)
  private val sliderRunnable: Runnable by lazy(LazyThreadSafetyMode.NONE) {
    Runnable { viewPager.currentItem += 1 }
  }

  // ==> This Handler and Runnable are used for auto-scrolling functionality in ViewPager2.
  // The sliderHandler posts a delayed sliderRunnable that transfer the viewPager to the next item.

  private val sliderViewPagerAdapter: SliderViewPagerAdapter by lazy(LazyThreadSafetyMode.NONE) {
    SliderViewPagerAdapter(viewPager = viewPager)
  }

  private val genreAdapter: GenreListAdapter by lazy(LazyThreadSafetyMode.NONE) {
    GenreListAdapter()
  }

  private val nowPlayingAdapter: MovieListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListHorizontalAdapter()
  }

  private val popularAdapter: MovieListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListHorizontalAdapter()
  }

  private val upComingAdapter: MovieListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListHorizontalAdapter()
  }

  private val topRatedAdapter: MovieListHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListHorizontalAdapter()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setUpNavigation()
    setUpSliderImg()
    setUpRecyclerView(
      mRecyclerView = binding.genreRecycleView,
      mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
      mAdapter = genreAdapter
    )
    setUpRecyclerView(
      mRecyclerView = binding.nowPlayingRecycleView,
      mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
      mAdapter = nowPlayingAdapter
    )
    setUpRecyclerView(
      mRecyclerView = binding.popularRecycleView,
      mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
      mAdapter = popularAdapter,
    )
    setUpRecyclerView(
      mRecyclerView = binding.upcomingRecycleView,
      mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
      mAdapter = upComingAdapter
    )
    setUpRecyclerView(
      mRecyclerView = binding.topRatedRecycleView,
      mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
      mAdapter = topRatedAdapter,
    )
    bindViewModel()
  }

  private fun setUpNavigation() {
    binding.apply {
      searchBtn.navigateTo(R.id.action_homeFragment_to_searchFragment)
      nowPlayingMoreBtn.navigateTo(R.id.action_homeFragment_to_nowPlayingFragment)
      popularMoreBtn.navigateTo(R.id.action_homeFragment_to_popularFragment)
      upcomingMoreBtn.navigateTo(R.id.action_homeFragment_to_upcomingFragment)
      topRatedMoreBtn.navigateTo(R.id.action_homeFragment_to_topRatedFragment)
      genreMoreBtn.navigateTo(R.id.action_homeFragment_to_genreListFragment)
    }

    nowPlayingAdapter.onItemClickListener = { it: MovieItemModel ->
      findNavController().navigate(
        HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
          movieId = it.id
        )
      )
    }

    popularAdapter.onItemClickListener = { it: MovieItemModel ->
      findNavController().navigate(
        HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
          movieId = it.id
        )
      )
    }

    upComingAdapter.onItemClickListener = { it: MovieItemModel ->
      findNavController().navigate(
        HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
          movieId = it.id
        )
      )
    }

    topRatedAdapter.onItemClickListener = { it: MovieItemModel ->
      findNavController().navigate(
        HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
          movieId = it.id
        )
      )
    }
  }

  private fun setUpSliderImg() {
    viewPager = binding.sliderViewPager
    viewPager.apply {
      adapter = sliderViewPagerAdapter
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
      // This transformer adds a margin between pages
      addTransformer(MarginPageTransformer(10))

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
      currentItem = 0

      // A page change callback is registered to listen for page selection events. This callback is
      // triggered whenever the current page of the ViewPager2 changes.
      registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
          super.onPageSelected(position)

          sliderHandler.removeCallbacks(sliderRunnable)
          sliderHandler.postDelayed(sliderRunnable, 2000)

          // This method is called when a new page is selected by user. It removes any pending callbacks for
          // the sliderRunnable (likely a runnable for auto-scrolling) and then posts a delayed execution
          // of the sliderRunnable with a delay of 2000 milliseconds (2 seconds). This likely restarts
          // the auto-scrolling timer whenever a new page is selected by user.
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
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  private fun renderUi(state: HomeUiState) {
    when (state) {
      HomeUiState.Loading -> {
        binding.apply {
          progressBar.visible()
          scrollView.invisible()
        }
      }

      is HomeUiState.Success -> {
        binding.apply {
          progressBar.invisible()
          scrollView.visible()
        }
        sliderViewPagerAdapter.submitList(state.nowPlaying)
        genreAdapter.submitList(state.genreList)
        nowPlayingAdapter.submitList(state.nowPlaying)
        popularAdapter.submitList(state.popular)
        upComingAdapter.submitList(state.upcoming)
        topRatedAdapter.submitList(state.topRated)
      }

      is HomeUiState.Error -> {
        binding.apply {
          progressBar.visible()
          scrollView.invisible()
        }
      }
    }
  }

  override fun onDestroyView() {
    // avoid memory leak
    binding.apply {
      sliderViewPager.adapter = null
      genreRecycleView.adapter = null
      nowPlayingRecycleView.adapter = null
      popularRecycleView.adapter = null
      upcomingRecycleView.adapter = null
      topRatedRecycleView.adapter = null
    }
    super.onDestroyView()
  }
}