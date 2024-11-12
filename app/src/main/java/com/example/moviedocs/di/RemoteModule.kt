package com.example.moviedocs.di

import com.example.moviedocs.BuildConfig
import com.example.moviedocs.data.remote.ApiService
import com.example.moviedocs.data.remote.interceptor.AuthorizationInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiKey

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiRequest

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
  @Provides
  @BaseUrl
  fun provideBaseUrl(): String = BuildConfig.BASE_URL
  
  @Provides
  @ApiKey
  fun provideApiKey(): String = BuildConfig.API_KEY
  
  @Provides
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
      level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
      } else {
        HttpLoggingInterceptor.Level.NONE
      }
    }
  
  @Provides
  @Singleton
  fun provideOkHttpClient(
    authorizationInterceptor: AuthorizationInterceptor,
    httpLoggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient =
    OkHttpClient.Builder()
      .connectTimeout(33, TimeUnit.SECONDS)
      .readTimeout(33, TimeUnit.SECONDS)
      .writeTimeout(33, TimeUnit.SECONDS)
      .addInterceptor(authorizationInterceptor)
      .addNetworkInterceptor(httpLoggingInterceptor)
      .build()
  
  @Provides
  @Singleton
  fun provideMoshi(): Moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory()) // to convert kotlin obj to json
    .build()
  
  @Provides
  @Singleton
  @ApiRequest
  fun provideRetrofit(
    moshi: Moshi,
    client: OkHttpClient,
    @BaseUrl baseUrl: String
  ): Retrofit =
    Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(client)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
  
  @Provides
  @Singleton
  fun provideApiService(
    @ApiRequest retrofit: Retrofit
  ): ApiService = retrofit.create()
}