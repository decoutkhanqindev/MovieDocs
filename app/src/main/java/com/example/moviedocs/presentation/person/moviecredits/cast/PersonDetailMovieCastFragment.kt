package com.example.moviedocs.presentation.person.moviecredits.cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedocs.databinding.FragmentPersonDetailMovieCreditsBinding
import com.example.moviedocs.domain.model.movielist.MovieItemModel
import com.example.moviedocs.domain.model.movielist.year.MovieListByYearModel
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.movielist.year.MovieListByYearAdapter
import com.example.moviedocs.presentation.movielist.year.MovieListItemByYearAdapter
import com.example.moviedocs.presentation.person.PersonDetailFragmentDirections
import com.example.moviedocs.presentation.person.PersonDetailUiState
import com.example.moviedocs.presentation.person.PersonDetailViewModel
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

  private var personId: Int = 0

  private val viewModel: PersonDetailViewModel by viewModels()

  private val movieListItemByYearAdapter: MovieListItemByYearAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListItemByYearAdapter()
  }

  private val movieListByYearAdapter: MovieListByYearAdapter by lazy(LazyThreadSafetyMode.NONE) {
    MovieListByYearAdapter(movieListItemByYearAdapter)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    personId = arguments?.getInt("personId") ?: 0
    return super.onCreateView(inflater, container, savedInstanceState)
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
    setUpNavigation()
    viewModel.setPersonId(personId)
    bindViewModel()
  }

  private fun setUpNavigation() {
    movieListItemByYearAdapter.setOnItemInAdapterClickListener { it: MovieItemModel ->
      findNavController().navigate(
        PersonDetailFragmentDirections.actionPersonDetailFragmentToMovieDetailGraph(
          movieId = it.id
        )
      )
    }
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  private fun renderUi(state: PersonDetailUiState) {
    when (state) {
      PersonDetailUiState.Loading -> {
        hideErrorDialog()

        binding.personDetailMovieCreditsRecyclerview.invisible()
      }

      is PersonDetailUiState.Success -> {
        hideErrorDialog()

        binding.personDetailMovieCreditsRecyclerview.visible()

        val movieListByYear: List<MovieListByYearModel> =
          viewModel.getMovieListByYear(state.castMovieCreditList)
        movieListByYearAdapter.submitList(movieListByYear)
      }

      is PersonDetailUiState.Error -> {
        binding.personDetailMovieCreditsRecyclerview.invisible()

        showErrorDialog { viewModel.loadData(personId) }
      }
    }
  }

  override fun onDestroyView() {
    binding.personDetailMovieCreditsRecyclerview.adapter = null
    super.onDestroyView()
  }
}