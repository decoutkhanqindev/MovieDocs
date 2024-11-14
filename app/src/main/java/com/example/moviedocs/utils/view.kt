package com.example.moviedocs.utils

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviedocs.R
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine

// Suspending function to start animation and await the end of the animation
suspend fun View.applyAnimationFadeIn(timeSpan: Long = 1000L) {
  if (!this.isVisible) this.visibility = View.VISIBLE
  
  suspendCancellableCoroutine { continuation: CancellableContinuation<Unit> ->
    ObjectAnimator.ofPropertyValuesHolder(
      this,
      PropertyValuesHolder.ofFloat("alpha", 0f, 1f), // fade-in
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
        // resume another animation of views at suspend point
        continuation.resumeWith(Result.success(Unit))
      }
      
      doOnCancel {
        continuation.resumeWith(Result.success(Unit))
      }
      
      start()
    }
  }
}

fun ImageView.loadImgFromUrl(url: String?) {
  Glide.with(context)
    .load("https://image.tmdb.org/t/p/w500$url")
    .error(R.drawable.app_icon)
    .fitCenter()
    .centerCrop()
    .transition(DrawableTransitionOptions.withCrossFade())
    .into(this)
}

fun View.navigateTo(actionId: Int) =
  this.setOnClickListener {
    findNavController().navigate(actionId)
  }

fun View.navigateBack() =
  this.setOnClickListener {
    findNavController().navigateUp()
  }


fun View.visible() {
  if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

fun View.gone() {
  if (visibility != View.GONE) visibility = View.GONE
}

fun View.invisible() {
  if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
}