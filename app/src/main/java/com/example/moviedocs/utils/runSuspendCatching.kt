package com.example.moviedocs.utils

import kotlinx.coroutines.withContext
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException

/*
* runSuspendCatching function is designed to execute a suspending function (block)
* within a coroutine and handle any exceptions that might occur during its execution.
* It returns a Result object, which encapsulates either the successful result of the
* function or the exception that was thrown.
*/

@OptIn(ExperimentalContracts::class)
suspend inline fun <T> runSuspendCatching(
  context: CoroutineContext = EmptyCoroutineContext,
  crossinline block: suspend () -> T,
): Result<T> {
  contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }

  return try {
    Result.success(withContext(context) {
      block()
    })
  } catch (c: CancellationException) {
    throw c
  } catch (e: Exception) {
    Result.failure(e)
  }
}