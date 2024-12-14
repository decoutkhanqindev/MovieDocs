package com.example.moviedocs.domain.person

import com.example.moviedocs.domain.model.movielist.MovieItemModel

data class PersonMovieCreditListModel(
  val cast: List<MovieItemModel>,
  val crew: List<MovieItemModel>,
  val id: Int // movie id
)