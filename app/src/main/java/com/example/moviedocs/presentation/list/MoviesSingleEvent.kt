package com.example.moviedocs.presentation.list

sealed interface MoviesSingleEvent {
  data object Success : MoviesSingleEvent
  data class Error(val throwable: Throwable) : MoviesSingleEvent
}