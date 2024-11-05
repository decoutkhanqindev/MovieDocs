package com.example.moviedocs.data.remote

import com.example.moviedocs.data.remote.response.list.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
  @GET("movie/now_playing")
  suspend fun getNowPlayingMovies(@Query("page") page: Int = 1): MoviesResponse
  
  @GET("movie/popular")
  suspend fun getPopularMovies(@Query("page") page: Int = 1): MoviesResponse
  
  @GET("movie/upcoming")
  suspend fun getUpcomingMovies(@Query("page") page: Int = 1): MoviesResponse
}