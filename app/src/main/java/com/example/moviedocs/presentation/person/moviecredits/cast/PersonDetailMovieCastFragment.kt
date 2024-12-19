package com.example.moviedocs.presentation.person.moviecredits.cast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.FragmentPersonDetailMovieCreditsBinding
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.domain.model.movielist.year.MovieListByYearModel
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.movielist.year.MovieListByYearAdapter
import com.example.moviedocs.presentation.person.PersonDetailUiState
import com.example.moviedocs.presentation.person.PersonDetailViewModel
import com.example.moviedocs.utils.formatDate
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.setUpRecyclerView
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailMovieCastFragment : BaseFragment<FragmentPersonDetailMovieCreditsBinding>(
  FragmentPersonDetailMovieCreditsBinding::inflate
) {
  companion object {
    fun newInstance(): PersonDetailMovieCastFragment = PersonDetailMovieCastFragment()
  }

  private val viewModel: PersonDetailViewModel by activityViewModels()

  private val movieListByYearAdapter: MovieListByYearAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListByYearAdapter()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setUpRecyclerView(
      mRecyclerView = binding.personDetailMovieCreditsRecyclerview,
      mLayoutManager = LinearLayoutManager(
        requireContext(), LinearLayoutManager.VERTICAL, false
      ),
      mAdapter = movieListByYearAdapter
    )
    bindViewModel()
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  private fun renderUi(state: PersonDetailUiState) {
    when (state) {
      PersonDetailUiState.Loading -> {
        binding.personDetailMovieCreditsRecyclerview.invisible()
      }

      is PersonDetailUiState.Success -> {
        binding.personDetailMovieCreditsRecyclerview.visible()

        val movieCredits: List<MovieItemModel> = state.castMovieCreditList
        val mapByYear: Map<String, List<MovieItemModel>> =
          movieCredits.filter { it.releaseDate.formatDate() != "Unknown date" }
            .groupBy { it: MovieItemModel -> it.releaseDate.formatDate().substring(6) }
        val movieListByYear: List<MovieListByYearModel> =
          mapByYear.map { (year: String, movies: List<MovieItemModel>) ->
            MovieListByYearModel(year, movies)
          }
        movieListByYearAdapter.submitList(movieListByYear.sortedByDescending { it.year })
      }

      is PersonDetailUiState.Error -> {
        binding.personDetailMovieCreditsRecyclerview.invisible()
      }
    }
  }

  override fun onDestroyView() {
    binding.personDetailMovieCreditsRecyclerview.adapter = null
    super.onDestroyView()
  }
}