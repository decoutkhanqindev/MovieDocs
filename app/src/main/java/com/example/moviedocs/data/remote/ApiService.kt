package com.example.moviedocs.data.remote

import com.example.moviedocs.data.remote.externalId.ExternalIdsResponse
import com.example.moviedocs.data.remote.response.company.CompanyDetailResponse
import com.example.moviedocs.data.remote.response.country.CountryItemResponse
import com.example.moviedocs.data.remote.response.credits.CreditListResponse
import com.example.moviedocs.data.remote.response.genre.GenreListResponse
import com.example.moviedocs.data.remote.response.language.LanguageItemResponse
import com.example.moviedocs.data.remote.response.moviedetails.MovieDetailResponse
import com.example.moviedocs.data.remote.response.moviedetails.image.MediaListResponse
import com.example.moviedocs.data.remote.response.movielist.MovieListResponse
import com.example.moviedocs.data.remote.response.person.PersonDetailResponse
import com.example.moviedocs.data.remote.response.person.PersonMovieCreditListResponse
import retrofit2.http.GET
import retrofit2.http.Path
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

  @GET("genre/movie/list")
  suspend fun getGenreList(): GenreListResponse

  // The annotation @Path tells Retrofit to replace the {movie_id} placeholder in the URL with the
  // value of the movieId parameter.

  @GET("movie/{movie_id}")
  suspend fun getMovieDetail(@Path("movie_id") movieId: Int): MovieDetailResponse

  @GET("movie/{movie_id}/external_ids")
  suspend fun getMovieDetailExternalIds(@Path("movie_id") movieId: Int): ExternalIdsResponse

  @GET("configuration/countries")
  suspend fun getCountryList(): List<CountryItemResponse>

  @GET("configuration/languages")
  suspend fun getLanguageList(): List<LanguageItemResponse>

  @GET("company/{company_id}")
  suspend fun getCompanyDetail(@Path("company_id") companyId: Int): CompanyDetailResponse

  @GET("discover/movie")
  suspend fun getCompanyMovieList(
    @Query("page") page: Int, @Query("with_companies") companyId: Int
  ): MovieListResponse

  @GET("movie/{movie_id}/credits")
  suspend fun getCreditList(@Path("movie_id") movieId: Int): CreditListResponse

  @GET("discover/movie")
  suspend fun getGenreMovieList(
    @Query("page") page: Int, @Query("with_genres") genreId: Int
  ): MovieListResponse

  @GET("person/{person_id}")
  suspend fun getPersonDetail(@Path("person_id") personId: Int): PersonDetailResponse

  @GET("person/{person_id}/external_ids")
  suspend fun getPersonDetailExternalIds(@Path("person_id") personId: Int): ExternalIdsResponse

  @GET("person/{person_id}/movie_credits")
  suspend fun getPersonMovieCreditList(
    @Path("person_id") personId: Int
  ): PersonMovieCreditListResponse

  @GET("movie/{movie_id}/images")
  suspend fun getMovieMedia(@Path("movie_id") movieId: Int): MediaListResponse
}