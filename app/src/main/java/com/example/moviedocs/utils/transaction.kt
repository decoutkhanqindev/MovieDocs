package com.example.moviedocs.utils

import androidx.fragment.app.Fragment
import com.example.moviedocs.R

fun Fragment.replaceFragment(
  fragment: Fragment,
  addToBackStack: Boolean = true,
  containerId: Int = R.id.fragmentContainer,
  animate: Boolean = true,
) {
  parentFragmentManager.beginTransaction().apply {
    if (animate) {
      setCustomAnimations(
        /* enter = */ R.anim.to_right,
        /* exit = */ R.anim.to_right,
        /* popEnter = */ R.anim.from_right,
        /* popExit = */ R.anim.from_right
      )
    }
    replace(containerId, fragment)
    if (addToBackStack) {
      addToBackStack(fragment::class.java.simpleName)
    }
    commit()
  }
}

fun Fragment.popBackStack() {
  parentFragmentManager.popBackStack()
}