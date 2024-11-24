package com.example.moviedocs.presentation.movielist

sealed interface MovieListSingleEvent {
  data object Success : MovieListSingleEvent
  data class Error(val throwable: Throwable) : MovieListSingleEvent
}