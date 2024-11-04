package com.example.moviedocs.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.io.InputStream

@GlideModule
class GlideModule : AppGlideModule() {
  override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    // get the entry point for the OkHttpClient
    val entryPoint: OkHttpEntryPoint = EntryPoints.get(context, OkHttpEntryPoint::class.java)
    
    // get OkHttpClient from the entry point
    val okHttpClient: OkHttpClient = entryPoint.provideOkHttpClient()
    
    registry.replace(
      GlideUrl::class.java,
      InputStream::class.java,
      OkHttpUrlLoader.Factory(okHttpClient)
    )
  }
  
  @EntryPoint
  @InstallIn(SingletonComponent::class)
  internal interface OkHttpEntryPoint {
    fun provideOkHttpClient(): OkHttpClient
  }
}