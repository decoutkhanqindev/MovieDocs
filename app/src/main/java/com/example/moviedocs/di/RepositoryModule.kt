package com.example.moviedocs.di

import com.example.moviedocs.data.repository.moviedetail.MovieDetailRepositoryImpl
import com.example.moviedocs.data.repository.moviegenre.GenreListRepositoryImpl
import com.example.moviedocs.data.repository.movielist.MovieListRepositoryImpl
import com.example.moviedocs.domain.repository.moviedetail.MovieDetailRepository
import com.example.moviedocs.domain.repository.moviegenre.GenreListRepository
import com.example.moviedocs.domain.repository.movielist.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
  @Binds
  fun bindMovieListRepository(
    repository: MovieListRepositoryImpl
  ): MovieListRepository
  
  @Binds
  fun bindGenreListRepository(
    repository: GenreListRepositoryImpl
  ): GenreListRepository
  
  @Binds
  fun bindMovieDetailRepository(
    repository: MovieDetailRepositoryImpl
  ): MovieDetailRepository
}