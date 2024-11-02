package com.example.moviedocs.domain.usecase.list

import com.example.moviedocs.domain.model.MovieModel
import com.example.moviedocs.domain.repository.MovieListRepository
import javax.inject.Inject

class GetMovieListNowPlayingUseCase
@Inject constructor(private val repository: MovieListRepository) {
  suspend operator fun invoke(): Result<List<MovieModel>> = repository.getMoviesNowPlaying()
}