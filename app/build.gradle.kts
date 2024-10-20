plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  id("com.google.devtools.ksp")
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
  
  // Retrofit
  implementation("com.squareup.retrofit2:retrofit:2.11.0")
  implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
  // Gson
  implementation("com.squareup.retrofit2:converter-gson:2.11.0")
  implementation("com.google.code.gson:gson:2.11.0")
  
  // Okhttp
  implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
  implementation("com.squareup.okhttp3:okhttp")
  implementation("com.squareup.okhttp3:logging-interceptor")
  
  // Coroutine
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.9")
  
  // Glide
  implementation("com.github.bumptech.glide:glide:4.16.0")
  implementation("com.github.bumptech.glide:okhttp3-integration:4.16.0")
  
  // Hilt
  implementation("com.google.dagger:hilt-android:2.51.1")
  kapt("com.google.dagger:hilt-android-compiler:2.51.1")
}