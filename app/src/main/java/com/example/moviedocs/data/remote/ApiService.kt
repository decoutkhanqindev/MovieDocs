package com.example.moviedocs.data.remote

import com.example.moviedocs.data.remote.response.list.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
  @GET("movie/now_playing")
  suspend fun getNowPlaying(@Query("page") page: Int): MovieListResponse
  
  @GET("movie/popular")
  suspend fun getPopular(@Query("page") page: Int): MovieListResponse
  
  @GET("movie/upcoming")
  suspend fun getUpcoming(@Query("page") page: Int): MovieListResponse
  
  @GET("movie/top_rated")
  suspend fun getTopRated(@Query("page") page: Int): MovieListResponse
}