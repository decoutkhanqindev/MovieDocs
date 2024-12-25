package com.example.moviedocs.presentation.movielist.year

import android.annotation.SuppressLint
import android.view.LayoutInflater
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

  override fun onCreateViewHolder(
    parent: ViewGroup, viewType: Int
  ): BaseViewHolder<MovieListByYearModel, MovieItemByYearViewHolderBinding> =
    VH(
      MovieItemByYearViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  inner class VH(
    binding: MovieItemByYearViewHolderBinding
  ) : BaseViewHolder<MovieListByYearModel, MovieItemByYearViewHolderBinding>(binding) {

    init {
      binding.apply {
        root.setOnClickListener {
          val position: Int = bindingAdapterPosition
          if (position != RecyclerView.NO_POSITION) {
            val item: MovieListByYearModel = getItem(position)
            item.isOpen = !item.isOpen
            notifyItemChanged(position)
          }
        }

        setUpRecyclerView(
          mSetHasFixedSize = true,
          mRecyclerView = movieListItemByYearRecyclerview,
          mLayoutManager = LinearLayoutManager(
            root.context, LinearLayoutManager.VERTICAL, false
          ),
          mAdapter = movieListItemByYearAdapter
        )
      }
    }

    @SuppressLint("SetTextI18n")
    override fun bind(item: MovieListByYearModel) {
      binding.apply {
        yearTitle.text = "${item.year} ${item.movies.size.formatTotalResult()}"
        movieListItemByYearAdapter.submitList(item.movies)

        if (item.isOpen) {
          openBtn.gone()
          closeBtn.visible()
          movieListItemByYearRecyclerview.visible()
        } else {
          openBtn.visible()
          closeBtn.gone()
          movieListItemByYearRecyclerview.gone()
        }
      }
    }
  }
}