package com.example.moviedocs.presentation

import android.os.Bundle
import com.example.moviedocs.databinding.ActivityMainBinding
import com.example.moviedocs.presentation.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }
}