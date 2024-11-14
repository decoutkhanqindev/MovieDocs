package com.example.moviedocs.data.mapper

import com.example.moviedocs.data.remote.response.list.MovieListResponse
import com.example.moviedocs.domain.model.list.MovieListModel

fun MovieListResponse.toMovieListModel(): MovieListModel =
  MovieListModel(
    page = page,
    results = results.map { it.toMovieModel() },
    totalPages = totalPages,
    totalResults = totalResults
  )