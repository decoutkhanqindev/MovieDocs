package com.example.moviedocs.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.moviedocs.utils.logLifecycle

abstract class BaseFragment<VB : ViewBinding>(
  private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : Fragment() {
  
  private var _binding: VB? = null
  protected val binding: VB get() = _binding!!
  
  private val className: String by lazy(LazyThreadSafetyMode.PUBLICATION) {
    this::class.java.simpleName
  }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logLifecycle("onCreate", className)
  }
  
  @CallSuper
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
  ): View? {
    logLifecycle("onCreateView", className)
    return inflate(inflater, container, false).also { _binding = it }.root
  }
  
  override fun onStart() {
    super.onStart()
    logLifecycle("onStart", className)
  }
  
  override fun onResume() {
    super.onResume()
    logLifecycle("onResume", className)
  }
  
  override fun onPause() {
    logLifecycle("onPause", className)
    super.onPause()
  }
  
  override fun onStop() {
    logLifecycle("onStop", className)
    super.onStop()
  }
  
  @CallSuper
  override fun onDestroyView() {
    logLifecycle("onDestroyView", className)
    _binding = null
    super.onDestroyView()
  }
  
  override fun onDestroy() {
    logLifecycle("onDestroy", className)
    super.onDestroy()
  }
}