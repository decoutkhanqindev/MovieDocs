package com.example.moviedocs.data.remote.interceptor

import com.example.moviedocs.di.ApiKey
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
  @ApiKey private val apiKey: String,
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val originalRequest: Request = chain.request()

    val newUrl: HttpUrl = originalRequest.url.newBuilder()
      .addQueryParameter(name = "api_key", value = apiKey)
      .build()

    val newRequest: Request = originalRequest.newBuilder()
      .url(newUrl)
      .build()

    return chain.proceed(newRequest)
  }
}