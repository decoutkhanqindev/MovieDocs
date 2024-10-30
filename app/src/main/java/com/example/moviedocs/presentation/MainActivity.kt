package com.example.moviedocs.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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