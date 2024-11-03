package com.example.moviedocs.data.remote

import com.example.moviedocs.data.remote.response.list.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
  @GET("movie/now_playing")
  suspend fun getMovieNowPlaying(@Query("page") page: Int = 1): MovieListResponse
  
  @GET("movie/popular")
  suspend fun getMoviePopular(@Query("page") page: Int = 1): MovieListResponse
  
  @GET("movie/top_rated")
  suspend fun getMovieTopRated(@Query("page") page: Int = 1): MovieListResponse
  
  @GET("movie/upcoming")
  suspend fun getMovieUpcoming(@Query("page") page: Int = 1): MovieListResponse
}