package com.example.moviedocs.presentation.movielist.year

import android.annotation.SuppressLint
import android.os.Parcelable
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

  private val scrollState: HashMap<String, Parcelable> = hashMapOf<String, Parcelable>()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder<MovieListByYearModel, MovieItemByYearViewHolderBinding> =
    VH(
      MovieItemByYearViewHolderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  override fun onViewRecycled(holder: BaseViewHolder<MovieListByYearModel, MovieItemByYearViewHolderBinding>) {
    super.onViewRecycled(holder)

    // save scroll state here
    val key: String = currentList[holder.bindingAdapterPosition].year
    val recyclerView: RecyclerView = holder.itemView.parent as RecyclerView
    val layoutManager: RecyclerView.LayoutManager? = recyclerView.layoutManager
    if (layoutManager != null) {
      scrollState[key] = layoutManager.onSaveInstanceState()!!
    }
  }

  override fun onBindViewHolder(
    holder: BaseViewHolder<MovieListByYearModel, MovieItemByYearViewHolderBinding>,
    position: Int
  ) {
    super.onBindViewHolder(holder, position)

    val key: String = currentList[holder.bindingAdapterPosition].year
    val state: Parcelable? = scrollState[key]
    val recyclerView: RecyclerView = holder.itemView.parent as RecyclerView
    val layoutManager: RecyclerView.LayoutManager? = recyclerView.layoutManager
    if (layoutManager != null) {
      if (state != null) {
        layoutManager.onRestoreInstanceState(state)
      } else {
        layoutManager.scrollToPosition(0)
      }
    }
  }

  private inner class VH(
    binding: MovieItemByYearViewHolderBinding
  ) : BaseViewHolder<MovieListByYearModel, MovieItemByYearViewHolderBinding>(binding) {

    init {
      binding.apply {
        root.setOnClickListener {
          val isOpen: Boolean = binding.movieListCreditsRecyclerview.visibility == View.GONE
          handleOnItemClick(isOpen)
        }

        setUpRecyclerView(
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