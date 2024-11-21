package com.example.moviedocs.utils

import androidx.core.app.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import timber.log.Timber

/*
 * These functions simplify the process of running suspend functions within a lifecycle-aware scope
 * that automatically respects the STARTED lifecycle state of the associated component.
 * 
 * (0) Parameters:
 * + "launchBlock: suspend () -> Unit": A required suspend function that will be launched
 * first within the STARTED lifecycle state.
 * + "vararg launchBlocks: suspend () -> Unit": Optional additional suspend functions that will
 * also be launched concurrently after the first block.
 * + "doAfterLaunch: (() -> Unit)? = null": An optional callback to execute after all the provided
 * suspend functions (launchBlock and launchBlocks) have been launched.
 * 
 * (1) Fragment:
 * + It ensures that suspend functions (launchBlock and additional launchBlocks)
 * are executed repeatedly while the Fragment's Lifecycle is in the STARTED state.
 * + "viewLifecycleOwner.lifecycleScope": Ensures the coroutine runs within the Fragment's view
 * lifecycle
 * + "viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)": Executes the block when the
 * lifecycle is at least STARTED, suspending it otherwise.
 * 
 * (2) Activity:
 * + Similar to the Fragment version but applies to the ComponentActivity.
 * + "lifecycleScope": Ensures the coroutine runs within the Activity lifecycle.
 * + "repeatOnLifecycle(Lifecycle.State.STARTED)": Same purpose as the Fragment version.
 * 
 * (3) How the Execution Works:
 * + For Each Coroutine: The function uses launch to start coroutines inside
 * "viewLifecycleOwner.lifecycleScope" or "lifecycleScope", depending on whether it's a Fragment
 * or ComponentActivity.
 * + Lifecycle Awareness: "repeatOnLifecycle(Lifecycle.State.STARTED)" ensures that the coroutines
 * will:
 * Runs the block of code in a coroutine when the lifecycle is at least STARTED.
 * The coroutine will be cancelled when the ON_STOP event happens and will
 * restart executing if the lifecycle receives the ON_START event again.
 * + Execution Flow:
 * Launch the required launchBlock.
 * Launch each additional block from launchBlocks concurrently.
 * Optionally, invoke the doAfterLaunch callback after launching all blocks.
 */

fun Fragment.launchAndRepeatStarted(
  launchBlock: suspend () -> Unit,
  vararg launchBlocks: suspend () -> Unit,
  doAfterLaunch: (() -> Unit)? = null,
) {
  viewLifecycleOwner.lifecycleScope.launch {
    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
      launch { launchBlock() }
      launchBlocks.forEach { it: suspend () -> Unit ->
        launch { it() }
      }
      doAfterLaunch?.invoke()
    }
  }
}

fun ComponentActivity.launchAndRepeatStarted(
  launchBlock: suspend () -> Unit,
  vararg launchBlocks: suspend () -> Unit,
  doAfterLaunch: (() -> Unit)? = null,
) {
  lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
      launch { launchBlock() }
      launchBlocks.forEach { it: suspend () -> Unit ->
        launch { it() }
      }
      doAfterLaunch?.invoke()
    }
  }
}

fun logLifecycle(event: String, className: String) {
  Timber.tag(className).d("$className: $event")
}