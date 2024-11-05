package com.example.moviedocs.di

import com.example.moviedocs.data.repository.MoviesRepositoryImpl
import com.example.moviedocs.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
  @Binds
  fun bindMoviesRepository(repository: MoviesRepositoryImpl): MoviesRepository
}