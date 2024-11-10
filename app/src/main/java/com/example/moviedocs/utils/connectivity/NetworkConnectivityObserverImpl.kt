package com.example.moviedocs.utils.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import timber.log.Timber
import javax.inject.Inject

/**
 * Observes network connectivity changes and emits the current network status as a Flow.
 *
 * This class uses the ConnectivityManager to monitor network connectivity and provides
 * a Flow that emits the current network status whenever it changes.
 */
class NetworkConnectivityObserverImpl
@Inject constructor( @ApplicationContext context: Context) : NetworkConnectivityObserver {
  
  // Get the ConnectivityManager system service
  private val connectivityManager: ConnectivityManager =
    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  
  override fun observe(): Flow<NetworkConnectivityObserver.NetworkStatus> =
    // Create a Flow using callbackFlow
    callbackFlow {
      val callback = object : NetworkCallback() {
        
        override fun onAvailable(network: Network) {
          super.onAvailable(network)
          channel.trySend(NetworkConnectivityObserver.NetworkStatus.AVAILABLE)
          Timber.tag("Network Status").d("AVAILABLE")
        }
        
        override fun onUnavailable() {
          super.onUnavailable()
          channel.trySend(NetworkConnectivityObserver.NetworkStatus.UNAVAILABLE)
          Timber.tag("Network Status").d("UNAVAILABLE")
        }
        
        override fun onLosing(network: Network, maxMsToLive: Int) {
          super.onLosing(network, maxMsToLive)
          channel.trySend(NetworkConnectivityObserver.NetworkStatus.LOSING)
          Timber.tag("Network Status").d("LOSING")
        }
        
        override fun onLost(network: Network) {
          super.onLost(network)
          channel.trySend(NetworkConnectivityObserver.NetworkStatus.LOST)
          Timber.tag("Network Status").d("LOST")
        }
      }
      
      val request: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()
      
      // Register the NetworkCallback with the ConnectivityManager
      connectivityManager.registerNetworkCallback(request, callback)
      
      // Unregister the NetworkCallback when the Flow is closed
      awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
      }
    }.distinctUntilChanged() // Only emit distinct status changes
}