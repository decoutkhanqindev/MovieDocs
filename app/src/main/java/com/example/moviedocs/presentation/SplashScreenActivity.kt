package com.example.moviedocs.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moviedocs.databinding.ActivitySplashScreenBinding
import com.example.moviedocs.utils.applyAnimationFadeIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
  private val binding: ActivitySplashScreenBinding by lazy(LazyThreadSafetyMode.NONE) {
    ActivitySplashScreenBinding.inflate(layoutInflater)
  }
  
  @SuppressLint("Recycle")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    
    lifecycleScope.launch {
      delay(500)
      binding.appLogoImg.applyAnimationFadeIn()
      delay(500)
      binding.titleAppText.applyAnimationFadeIn()
      delay(500)
      binding.authorText.applyAnimationFadeIn()
      delay(1000)
      val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
      startActivity(intent)
      finish()
    }
  }
}