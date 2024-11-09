package com.example.moviedocs.presentation

import android.app.AlertDialog
import android.os.Bundle
import com.example.moviedocs.R
import com.example.moviedocs.databinding.ActivityMainBinding
import com.example.moviedocs.presentation.base.BaseActivity
import com.example.moviedocs.utils.connectivity.NetworkConnectivityObserver
import com.example.moviedocs.utils.launchAndRepeatStarted
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
  @Inject
  internal lateinit var networkConnectivityObserver: NetworkConnectivityObserver
  
  private var alertDialog: AlertDialog? = null
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    observeNetworkConnectivity()
  }
  
  private fun observeNetworkConnectivity() {
    launchAndRepeatStarted(
      { networkConnectivityObserver.observe().collect(::renderAlertDialog) }
    )
  }
  
  private fun renderAlertDialog(status: NetworkConnectivityObserver.NetworkStatus) {
    when (status) {
      NetworkConnectivityObserver.NetworkStatus.AVAILABLE -> hideAlertDialog()
      NetworkConnectivityObserver.NetworkStatus.LOSING -> showAlertDialog("losing")
      NetworkConnectivityObserver.NetworkStatus.LOST -> showAlertDialog("lost")
      NetworkConnectivityObserver.NetworkStatus.UNAVAILABLE -> showAlertDialog("unavailable")
    }
  }
  
  private fun showAlertDialog(message: String) {
    alertDialog = AlertDialog.Builder(this)
      .setTitle("Network Connectivity")
      .setMessage("Your network is $message \nPlease check your internet connection.")
      .setIcon(R.drawable.no_internet_connection)
      .setPositiveButton("OK", null)
      .show()
  }
  
  private fun hideAlertDialog() {
    alertDialog?.dismiss()
  }
}