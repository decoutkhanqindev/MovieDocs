package com.example.moviedocs.presentation.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.moviedocs.databinding.CustomErrorDialogLayoutBinding
import com.example.moviedocs.utils.logLifecycle

abstract class BaseFragment<VB : ViewBinding>(
  private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : Fragment() {

  private var _binding: VB? = null
  protected val binding: VB get() = _binding!!

  private val className: String by lazy(LazyThreadSafetyMode.PUBLICATION) {
    this::class.java.simpleName
  }

  private var alertDialog: AlertDialog? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logLifecycle(className, "onCreate")
  }

  @CallSuper
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
  ): View? {
    logLifecycle(className, "onCreateView")
    return inflate(inflater, container, false).also { _binding = it }.root
  }

  override fun onStart() {
    super.onStart()
    logLifecycle(className, "onStart")
  }

  override fun onResume() {
    super.onResume()
    logLifecycle(className, "onResume")
  }

  override fun onPause() {
    logLifecycle(className, "onPause")
    super.onPause()
  }

  override fun onStop() {
    logLifecycle(className, "onStop")
    super.onStop()
  }

  @CallSuper
  override fun onDestroyView() {
    logLifecycle(className, "onDestroyView")
    hideErrorDialog()
    _binding = null
    super.onDestroyView()
  }

  override fun onDestroy() {
    logLifecycle(className, "onDestroy")
    super.onDestroy()
  }

  fun showErrorDialog(block: () -> Unit) {
    hideErrorDialog()

    val binding: CustomErrorDialogLayoutBinding =
      CustomErrorDialogLayoutBinding.inflate(layoutInflater)

    if (alertDialog?.isShowing == true) return
    else {
      alertDialog = AlertDialog.Builder(requireContext())
        .setView(binding.root)
        .setCancelable(false)
        .create()
        .also { it.show() }
    }

    binding.tryAgainBtn.setOnClickListener { block() }
  }

  fun hideErrorDialog() {
    alertDialog?.dismiss()
    alertDialog = null
  }
}