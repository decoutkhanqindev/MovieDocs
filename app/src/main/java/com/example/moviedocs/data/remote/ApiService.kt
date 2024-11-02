package com.example.moviedocs.data.remote

import com.example.moviedocs.data.remote.response.list.MovieListResponse
import retrofit2.http.GET

interface ApiService {
  @GET("movie/now_playing")
  suspend fun getMovieNowPlaying(): MovieListResponse
  
  @GET("movie/popular")
  suspend fun getMoviePopular(): MovieListResponse
  
  @GET("movie/top_rated")
  suspend fun getMovieTopRated(): MovieListResponse
  
  @GET("movie/upcoming")
  suspend fun getMovieUpcoming(): MovieListResponse
}