package com.example.moviedocs.data.mapper.person

import com.example.moviedocs.data.mapper.movielist.toMovieItemModel
import com.example.moviedocs.data.remote.response.person.PersonMovieCreditListResponse
import com.example.moviedocs.domain.model.person.PersonMovieCreditListModel

fun PersonMovieCreditListResponse.toPersonMovieCreditListModel(): PersonMovieCreditListModel =
  PersonMovieCreditListModel(
    cast = cast.map { it.toMovieItemModel() },
    crew = crew.map { it.toMovieItemModel() },
    id = id
  )