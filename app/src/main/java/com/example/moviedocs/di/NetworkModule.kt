package com.example.moviedocs.di

import com.example.moviedocs.utils.connectivity.NetworkConnectivityObserver
import com.example.moviedocs.utils.connectivity.NetworkConnectivityObserverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
  @Binds
  @Singleton
  fun bindNetworkConnectivityObserver(observerImpl: NetworkConnectivityObserverImpl): NetworkConnectivityObserver
}