package com.example.moviedocs.presentation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moviedocs.R
import com.example.moviedocs.databinding.ActivityMainBinding
import com.example.moviedocs.databinding.CustomNetworkDialogLayoutBinding
import com.example.moviedocs.presentation.base.BaseActivity
import com.example.moviedocs.utils.connectivity.NetworkConnectivityObserver
import com.example.moviedocs.utils.gone
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
  
  @Inject
  internal lateinit var networkConnectivityObserver: NetworkConnectivityObserver
  
  private lateinit var navController: NavController
  
  private var alertDialog: AlertDialog? = null
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    setUpNavigation()
    observeNetworkConnectivity()
  }
  
  private fun setUpNavigation() {
    val navHostFragment: NavHostFragment = binding.mainNavHost.getFragment()
    navController = navHostFragment.navController
    binding.bottomNavigationMenu.setupWithNavController(navController)
    
    navController.addOnDestinationChangedListener { _: NavController, destination: NavDestination, _: Bundle? ->
      handleBottomNavigationMenuVisibility(destination.id)
    }
  }
  
  private fun handleBottomNavigationMenuVisibility(destination: Int) {
    when (destination) {
      R.id.homeFragment, R.id.favoriteFragment, R.id.profileFragment -> binding.bottomNavigationMenu.visible()
      else -> binding.bottomNavigationMenu.gone()
    }
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
    val dialogLayoutBinding: CustomNetworkDialogLayoutBinding =
      CustomNetworkDialogLayoutBinding.inflate(layoutInflater)
    
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