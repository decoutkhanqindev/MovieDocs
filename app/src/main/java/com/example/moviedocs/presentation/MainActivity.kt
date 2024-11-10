package com.example.moviedocs.presentation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.example.moviedocs.R
import com.example.moviedocs.databinding.ActivityMainBinding
import com.example.moviedocs.databinding.CustomNetworkDialogLayoutBinding
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
      { networkConnectivityObserver.observe().collect(::renderAlertDialog) },
    )
  }
  
  private fun renderAlertDialog(status: NetworkConnectivityObserver.NetworkStatus) {
    when (status) {
      NetworkConnectivityObserver.NetworkStatus.AVAILABLE -> hideAlertDialog()
      else -> showAlertDialog()
    }
  }
  
  private fun showAlertDialog() {
    val dialogLayoutBinding: CustomNetworkDialogLayoutBinding = CustomNetworkDialogLayoutBinding.inflate(layoutInflater)
    
    if (alertDialog?.isShowing == true) return
    alertDialog = AlertDialog.Builder(this)
      .setView(dialogLayoutBinding.root)
      .setCancelable(false)
      .show()
    
    dialogLayoutBinding.tryAgainBtn.setOnClickListener {
      // open wifi settings
      startActivity(Intent(android.provider.Settings.ACTION_WIFI_SETTINGS))
    }
  }
  
  private fun hideAlertDialog() {
    alertDialog?.dismiss()
    alertDialog = null
  }
}