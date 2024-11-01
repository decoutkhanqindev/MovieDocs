package com.example.moviedocs.di

import com.example.moviedocs.data.repository.MovieListRepositoryImpl
import com.example.moviedocs.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
  @Binds
  fun bindMovieListRepository(repository: MovieListRepositoryImpl): MovieListRepository
}