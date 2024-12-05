package com.example.moviedocs.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.moviedocs.utils.logLifecycle

abstract class BaseActivity<VB : ViewBinding>(
  private val inflate: (LayoutInflater) -> VB,
) : AppCompatActivity() {

  protected lateinit var binding: VB

  private val className: String by lazy(LazyThreadSafetyMode.PUBLICATION) {
    this::class.java.simpleName
  }

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = inflate(layoutInflater)
    setContentView(binding.root)

    logLifecycle(className, "onCreate")
  }

  override fun onStart() {
    super.onStart()
    logLifecycle(className, "onStart")
  }

  override fun onResume() {
    super.onResume()
    logLifecycle(className, "onResume")
  }

  override fun onPause() {
    logLifecycle(className, "onPause")
    super.onPause()
  }

  override fun onStop() {
    logLifecycle(className, "onStop")
    super.onStop()
  }

  override fun onDestroy() {
    logLifecycle(className, "onDestroy")
    super.onDestroy()
  }
}