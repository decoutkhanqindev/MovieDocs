package com.example.moviedocs.data.remote

import com.example.moviedocs.data.remote.response.list.MovieResponse
import retrofit2.http.GET

interface ApiService {
  @GET("movie/now_playing")
  suspend fun getMovieNowPlaying(): List<MovieResponse>
  
  @GET("movie/popular")
  suspend fun getMoviePopular(): List<MovieResponse>
  
  @GET("movie/top_rated")
  suspend fun getMovieTopRated(): List<MovieResponse>
  
  @GET("movie/upcoming")
  suspend fun getMovieUpcoming(): List<MovieResponse>
}