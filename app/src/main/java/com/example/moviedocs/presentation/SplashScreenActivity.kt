package com.example.moviedocs.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.moviedocs.databinding.ActivitySplashScreenBinding
import com.example.moviedocs.presentation.base.BaseActivity
import com.example.moviedocs.utils.applyAnimationFadeIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>(ActivitySplashScreenBinding::inflate) {
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
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