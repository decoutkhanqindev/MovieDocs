plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.example.moviedocs"
  compileSdk = 34
  
  defaultConfig {
    applicationId = "com.example.moviedocs"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
    
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  
  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  
  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  
  // Fragment
  implementation("androidx.fragment:fragment-ktx:1.8.4")
  
  // ViewModel
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
  // Saved state module for ViewModel
  implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.6")
  
  // ViewPager2
  implementation("androidx.viewpager2:viewpager2:1.1.0")
  
  // Retrofit
  implementation("com.squareup.retrofit2:retrofit:2.11.0")
  implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
  // Gson
  implementation("com.squareup.retrofit2:converter-gson:2.11.0")
  implementation("com.google.code.gson:gson:2.11.0")
  
  // Okhttp
  implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
  implementation("com.squareup.okhttp3:okhttp:4.12.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
  
  // Coroutine
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
  
  // Glide
  implementation("com.github.bumptech.glide:glide:4.16.0")
  implementation("com.github.bumptech.glide:okhttp3-integration:4.16.0")
  
  // Hilt
  implementation("com.google.dagger:hilt-android:2.52")
  kapt("com.google.dagger:hilt-android-compiler:2.52")
  
  // Room
  implementation("androidx.room:room-runtime:2.6.1")
  annotationProcessor("androidx.room:room-compiler:2.6.1")
  // optional - Kotlin Extensions and Coroutines support for Room
  implementation("androidx.room:room-ktx:2.6.1")
  kapt("androidx.room:room-compiler:2.6.1")
  
  // Timber
  implementation ("com.jakewharton.timber:timber:5.0.1")
  
  // App Startup
  implementation("androidx.startup:startup-runtime:1.1.1")
}