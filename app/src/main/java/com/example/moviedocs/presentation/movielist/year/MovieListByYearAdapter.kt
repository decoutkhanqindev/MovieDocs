package com.example.moviedocs.presentation.movielist.year

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedocs.databinding.MovieItemByYearViewHolderBinding
import com.example.moviedocs.domain.model.movielist.year.MovieListByYearModel
import com.example.moviedocs.presentation.base.BaseListAdapter
import com.example.moviedocs.presentation.base.BaseViewHolder
import com.example.moviedocs.utils.formatTotalResult
import com.example.moviedocs.utils.gone
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.visible

class MovieListByYearAdapter(
  private val movieListItemByYearAdapter: MovieListItemByYearAdapter
) : BaseListAdapter<MovieListByYearModel, MovieItemByYearViewHolderBinding>(
  MovieItemByYearDiffCallBack
) {

  private val sharedViewPool = RecyclerView.RecycledViewPool()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder<MovieListByYearModel, MovieItemByYearViewHolderBinding> =
    VH(
      MovieItemByYearViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )


  private inner class VH(
    binding: MovieItemByYearViewHolderBinding
  ) : BaseViewHolder<MovieListByYearModel, MovieItemByYearViewHolderBinding>(binding) {

    init {
      binding.apply {
        movieListCreditsRecyclerview.setRecycledViewPool(sharedViewPool)

        root.setOnClickListener {
          val isOpen: Boolean = binding.movieListCreditsRecyclerview.visibility == View.GONE
          handleOnItemClick(isOpen)
        }

        setUpRecyclerView(
          mSetHasFixedSize = true,
          mRecyclerView = movieListCreditsRecyclerview,
          mLayoutManager = LinearLayoutManager(
            root.context, LinearLayoutManager.VERTICAL, false
          ),
          mAdapter = movieListItemByYearAdapter
        )

        openBtn.visible()
        closeBtn.gone()
        movieListCreditsRecyclerview.gone()
      }
    }

    @SuppressLint("SetTextI18n")
    override fun bind(item: MovieListByYearModel) {
      binding.apply {
        yearTitle.text = "${item.year} ${item.movies.size.formatTotalResult()}"
        movieListItemByYearAdapter.submitList(item.movies)
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