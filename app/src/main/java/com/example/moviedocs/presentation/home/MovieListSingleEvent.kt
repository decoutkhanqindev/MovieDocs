package com.example.moviedocs.presentation.home

sealed interface MovieListSingleEvent {
  data object Success : MovieListSingleEvent
  data class Error(val throwable: Throwable) : MovieListSingleEvent
}