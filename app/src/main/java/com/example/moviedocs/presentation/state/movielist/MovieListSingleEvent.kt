package com.example.moviedocs.presentation.state.movielist

sealed interface MovieListSingleEvent {
  data object Success : MovieListSingleEvent
  data class Error(val throwable: Throwable) : MovieListSingleEvent
}