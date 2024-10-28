package com.example.moviedocs

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviedocs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
    ActivityMainBinding.inflate(layoutInflater)
  }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }
}