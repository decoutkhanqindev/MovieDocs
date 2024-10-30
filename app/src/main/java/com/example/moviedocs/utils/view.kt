package com.example.moviedocs.utils

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine

// Suspending function to start animation and await the end of the animation
suspend fun View.applyAnimationFadeIn(timeSpan: Long = 1000L) {
  if (!this.isVisible) this.visibility = View.VISIBLE
  
  suspendCancellableCoroutine { continuation: CancellableContinuation<Unit> ->
    ObjectAnimator.ofPropertyValuesHolder(
      this, PropertyValuesHolder.ofFloat("alpha", 0f, 1f), // fade-in
      PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f), // Zoom along X axis
      PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f)  // Zoom along Y axis
    ).apply {
      duration = timeSpan
      interpolator = AccelerateDecelerateInterpolator() // Smooth motion
      
//      addListener(object : AnimatorListenerAdapter() {
//        override fun onAnimationEnd(animation: Animator) {
//          continuation.resumeWith(Result.success(Unit))
//        }
//
//        override fun onAnimationCancel(animation: Animator) {
//          continuation.resumeWith(Result.success(Unit))
//        }
//      })
      
      // or
      
      doOnEnd {
        continuation.resumeWith(Result.success(Unit))
      }
      
      doOnCancel {
        continuation.resumeWith(Result.success(Unit))
      }
      
      start()
    }
  }
}

fun invisibleView(view: View) {
  view.visibility = View.INVISIBLE
}

fun visibleView(view: View) {
  view.visibility = View.VISIBLE
}