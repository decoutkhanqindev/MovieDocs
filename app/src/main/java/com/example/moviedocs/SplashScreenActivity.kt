package com.example.moviedocs

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moviedocs.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
  private val binding: ActivitySplashScreenBinding by lazy(LazyThreadSafetyMode.NONE) {
    ActivitySplashScreenBinding.inflate(layoutInflater)
  }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    
    lifecycleScope.launch {
      delay(2000)
      val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
      startActivity(intent)
      finish()
    }
  }
}