package com.example.moviedocs.presentation.home

sealed interface MoviesSingleEvent {
  data object Success : MoviesSingleEvent
  data class Error(val throwable: Throwable) : MoviesSingleEvent
}