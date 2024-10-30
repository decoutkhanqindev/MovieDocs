package com.example.moviedocs.utils

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.isVisible

fun applyAnimationFadeIn(view: View, timeSpan: Long) {
  if (!view.isVisible) view.visibility = View.VISIBLE
  ObjectAnimator.ofPropertyValuesHolder(
    view, PropertyValuesHolder.ofFloat("alpha", 0f, 1f), // fade-in
    PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f),    // Zoom along X axis
    PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f)     // Zoom along Y axis
  ).apply {
    duration = timeSpan
    interpolator = AccelerateDecelerateInterpolator() // Smooth motion
    start()
  }
}

fun invisibleView(view: View) {
  view.visibility = View.INVISIBLE
}

fun visibleView(view: View) {
  view.visibility = View.VISIBLE
}