package com.example.moviedocs.utils.connectivity

import kotlinx.coroutines.flow.Flow

/**
 * Interface for observing network connectivity changes.
 *
 * This interface provides a way to monitor the network status of the device
 * and receive updates whenever the connectivity changes.
 */
interface NetworkConnectivityObserver {
  
  // Observes network connectivity changes and emits the current network status.
  fun observe(): Flow<Status>
  
  // This enum represents the different states of network connectivity:
  // - AVAILABLE: The device is connected to a network and has internet access.
  // - UNAVAILABLE: The device is not connected to any network.
  // - LOSING: The device is losing its network connection.
  // - LOST: The device has lost its network connection.
  enum class Status {
    AVAILABLE, UNAVAILABLE, LOSING, LOST
  }
}