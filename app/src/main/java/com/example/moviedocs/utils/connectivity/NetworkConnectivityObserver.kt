package com.example.moviedocs.utils.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Observes network connectivity changes and emits the current network status as a Flow.
 *
 * This class uses the ConnectivityManager to monitor network connectivity and provides
 * a Flow that emits the current network status whenever it changes.
 */
class NetworkConnectivityObserver(context: Context) : ConnectivityObserver {
  
  // Get the ConnectivityManager system service
  private val connectivityManager: ConnectivityManager =
    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  
  override fun observe(): Flow<ConnectivityObserver.Status> =
    // Create a Flow using callbackFlow
    callbackFlow {
      val callback = object : NetworkCallback() {
        
        override fun onAvailable(network: Network) {
          super.onAvailable(network)
          launch {
            send(ConnectivityObserver.Status.AVAILABLE)
            Timber.tag("Network Status").d("AVAILABLE")
          }
        }
        
        override fun onUnavailable() {
          super.onUnavailable()
          launch {
            send(ConnectivityObserver.Status.UNAVAILABLE)
            Timber.tag("Network Status").d("UNAVAILABLE")
          }
        }
        
        override fun onLosing(network: Network, maxMsToLive: Int) {
          super.onLosing(network, maxMsToLive)
          launch {
            send(ConnectivityObserver.Status.LOSING)
            Timber.tag("Network Status").d("LOSING")
          }
        }
        
        override fun onLost(network: Network) {
          super.onLost(network)
          launch {
            send(ConnectivityObserver.Status.LOST)
            Timber.tag("Network Status").d("LOST")
          }
        }
      }
      
      // Register the NetworkCallback with the ConnectivityManager
      connectivityManager.registerDefaultNetworkCallback(callback)
      
      // Unregister the NetworkCallback when the Flow is closed
      awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
      }
    }.distinctUntilChanged() // Only emit distinct status changes
}