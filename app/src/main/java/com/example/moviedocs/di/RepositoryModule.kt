package com.example.moviedocs.di

import com.example.moviedocs.data.repository.genre.GenreListRepositoryImpl
import com.example.moviedocs.data.repository.list.MovieListRepositoryImpl
import com.example.moviedocs.domain.repository.genre.GenreListRepository
import com.example.moviedocs.domain.repository.list.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
  @Binds
  fun bindMovieListRepository(repository: MovieListRepositoryImpl): MovieListRepository
  
  @Binds
  fun bindGenreListRepository(repository: GenreListRepositoryImpl): GenreListRepository
}