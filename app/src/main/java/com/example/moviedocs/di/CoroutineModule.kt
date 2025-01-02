package com.example.moviedocs.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

enum class DispatcherType {
  Default, IO, Main
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppDispatcher(val type: DispatcherType)

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {
  @Provides
  @Singleton
  @AppDispatcher(DispatcherType.Default)
  fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

  @Provides
  @Singleton
  @AppDispatcher(DispatcherType.IO)
  fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @Singleton
  @AppDispatcher(DispatcherType.Main)
  fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}