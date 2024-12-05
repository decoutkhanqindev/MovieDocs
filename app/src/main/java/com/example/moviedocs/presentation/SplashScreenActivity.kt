package com.example.moviedocs.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.moviedocs.databinding.ActivitySplashScreenBinding
import com.example.moviedocs.presentation.base.BaseActivity
import com.example.moviedocs.utils.applyAnimationFadeIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>(
  ActivitySplashScreenBinding::inflate
) {

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.root.id)) { v, insets ->
      val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(
        systemBarInsets.left,
        systemBarInsets.top,
        systemBarInsets.right,
        systemBarInsets.bottom
      )
      insets
    }

    lifecycleScope.launch {
      delay(200)
      binding.appLogoImg.applyAnimationFadeIn(500)
      delay(200)
      binding.titleAppText.applyAnimationFadeIn(500)
      delay(200)
      binding.authorText.applyAnimationFadeIn(500)
      delay(500)
      val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
      startActivity(intent)
      finish()
    }
  }
}