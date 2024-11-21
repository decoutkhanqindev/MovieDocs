package com.example.moviedocs.presentation.home.state

sealed interface MovieListSingleEvent {
  data object Success : MovieListSingleEvent
  data class Error(val throwable: Throwable) : MovieListSingleEvent
}