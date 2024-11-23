package com.example.moviedocs.data.mapper.movielist

import com.example.moviedocs.data.remote.response.movielist.MovieListResponse
import com.example.moviedocs.domain.model.movielist.MovieListModel

fun MovieListResponse.toMovieListModel(): MovieListModel =
  MovieListModel(
    page = page,
    results = results.map { it.toMovieModel() },
    totalPages = totalPages,
    totalResults = totalResults
  )