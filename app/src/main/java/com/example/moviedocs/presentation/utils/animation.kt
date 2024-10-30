package com.example.moviedocs.presentation.utils

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

fun applyAnimationFadeIn(view: View, timeSpan: Long) {
  if (view.visibility != View.VISIBLE) {
    view.visibility = View.VISIBLE
    val animator: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
      view, PropertyValuesHolder.ofFloat("alpha", 0f, 1f),       // fade-in
      PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f),    // Zoom along X axis
      PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f)     // Zoom along Y axis
    )
    animator.apply {
      duration = timeSpan
      interpolator = AccelerateDecelerateInterpolator() // Smooth motion
      start()
    }
  }
}
