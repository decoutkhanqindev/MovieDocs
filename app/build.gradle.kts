import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android) version "2.1.0"
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
  id("com.google.gms.google-services")
  id("androidx.navigation.safeargs.kotlin")
}

android {
  namespace = "com.example.moviedocs"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.example.moviedocs"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    debug {
      // Read local.properties
      val properties: Properties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
      }

      val API_KEY: String = checkNotNull(properties.getProperty("API_KEY")) {
        "API_KEY is not set in local.properties"
      }
      val BASE_URL: String = checkNotNull(properties.getProperty("BASE_URL")) {
        "BASE_URL is not set in local.properties"
      }

      // Write API_KEY and BASE_URL to buildConfigFields
      buildConfigField(type = "String", name = "API_KEY", value = """"$API_KEY"""")
      buildConfigField(type = "String", name = "BASE_URL", value = """"$BASE_URL"""")
    }

    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }

  buildFeatures {
    viewBinding = true
    buildConfig = true
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  implementation(libs.androidx.core.animation)
  implementation(libs.androidx.annotation)
  implementation(libs.androidx.lifecycle.livedata.ktx)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  // Fragment
  implementation("androidx.fragment:fragment-ktx:1.8.5")

  // ViewModel
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
  implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.7")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx-android:2.8.7")

  // ViewPager2
  implementation("androidx.viewpager2:viewpager2:1.1.0")

  // Retrofit
  implementation("com.squareup.retrofit2:retrofit:2.11.0")
  implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
  // Moshi
  implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
  implementation("com.squareup.moshi:moshi-kotlin:1.15.1")

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
  implementation("com.google.dagger:hilt-android:2.53.1")
  ksp("com.google.dagger:hilt-android-compiler:2.53.1")

  // Room
  implementation("androidx.room:room-runtime:2.6.1")
  annotationProcessor("androidx.room:room-compiler:2.6.1")
  // optional - Kotlin Extensions and Coroutines support for Room
  implementation("androidx.room:room-ktx:2.6.1")
  ksp("androidx.room:room-compiler:2.6.1")

  // Timber
  implementation("com.jakewharton.timber:timber:5.0.1")

  // App Startup
  implementation("androidx.startup:startup-runtime:1.2.0")

  // Splash Screen
  implementation("androidx.core:core-splashscreen:1.0.1")

  // Navigation
  implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
  implementation("androidx.navigation:navigation-fragment:2.8.4")
  implementation("androidx.navigation:navigation-ui:2.8.4")

  // Data Store
  implementation("androidx.datastore:datastore-preferences-core:1.1.1")

  // Firebase
  implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
  implementation("com.google.firebase:firebase-auth:23.1.0")
  implementation("com.google.firebase:firebase-database:21.0.0")

  // Read more TextView
  implementation("kr.co.prnd:readmore-textview:1.0.0")

  // Paging
  implementation("androidx.paging:paging-runtime:3.3.5")
}