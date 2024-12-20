package com.example.moviedocs.presentation.movielist.year

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.MovieItemByYearViewHolderBinding
import com.example.moviedocs.domain.model.movielist.year.MovieListByYearModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.utils.gone
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.visible

class MovieListByYearAdapter :
  BaseListAdapter<MovieListByYearModel, MovieItemByYearViewHolderBinding>(
    MovieItemByYearDiffCallBack
  ) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseListAdapter<MovieListByYearModel, MovieItemByYearViewHolderBinding>.BaseViewHolder =
    VH(
      MovieItemByYearViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  private inner class VH(
    binding: MovieItemByYearViewHolderBinding
  ) : BaseViewHolder(binding) {

    private val movieListItemByYearAdapter: MovieListItemByYearAdapter by lazy(LazyThreadSafetyMode.NONE) {
      MovieListItemByYearAdapter()
    }

    init {
      binding.root.setOnClickListener {
        val isOpen: Boolean = binding.movieListCreditsRecyclerview.visibility == View.GONE
        handleOnItemClick(isOpen)
      }
    }

    override fun bind(item: MovieListByYearModel) {
      binding.apply {
        yearTitle.text = item.year
        setUpRecyclerView(
          mRecyclerView = movieListCreditsRecyclerview,
          mLayoutManager = LinearLayoutManager(
            root.context, LinearLayoutManager.VERTICAL, false
          ),
          mAdapter = movieListItemByYearAdapter
        )
        movieListItemByYearAdapter.submitList(item.movies)
        openBtn.visible()
        closeBtn.gone()
        movieListCreditsRecyclerview.gone()
      }
    }

    private fun handleOnItemClick(isOpen: Boolean) {
      binding.apply {
        if (isOpen == false) {
          openBtn.visible()
          closeBtn.gone()
          movieListCreditsRecyclerview.gone()
        } else {
          openBtn.gone()
          closeBtn.visible()
          movieListCreditsRecyclerview.visible()
        }
      }
    }
  }
}