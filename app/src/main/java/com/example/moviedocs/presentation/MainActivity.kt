package com.example.moviedocs.presentation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
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
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

  @Inject
  internal lateinit var networkConnectivityObserver: NetworkConnectivityObserver

  private var alertDialog: AlertDialog? = null

  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(
        systemBarsInsets.left,
        systemBarsInsets.top,
        systemBarsInsets.right,
        systemBarsInsets.bottom
      )
      insets
    }

    setUpBottomNavigationMenu()
    observeNetworkConnectivity()
  }

  private fun setUpBottomNavigationMenu() {
    val navHostFragment: NavHostFragment = binding.mainNavHost.getFragment()
    navController = navHostFragment.navController

    navController.addOnDestinationChangedListener { _, destination, _ ->
      when (destination.id) {
        R.id.homeFragment, R.id.favoriteFragment, R.id.profileFragment -> {
          launchAndRepeatStarted({
            delay(200)
            binding.bottomNavigationMenu.visible()
          })
        }

        else -> binding.bottomNavigationMenu.gone()
      }
    }

    binding.bottomNavigationMenu.setupWithNavController(navController)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
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
    alertDialog = AlertDialog.Builder(this).setView(dialogLayoutBinding.root)
      .setCancelable(false) // can not close a dialog if network is unavailable
      .show()

    dialogLayoutBinding.tryAgainBtn.setOnClickListener {
      // open wifi settings
      val intent = Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)
      startActivity(intent)
    }
  }

  private fun hideAlertDialog() {
    alertDialog?.dismiss()
    alertDialog = null
  }
}