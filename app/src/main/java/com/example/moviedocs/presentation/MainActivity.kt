package com.example.moviedocs.presentation

import com.example.moviedocs.databinding.ActivityMainBinding
import com.example.moviedocs.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)