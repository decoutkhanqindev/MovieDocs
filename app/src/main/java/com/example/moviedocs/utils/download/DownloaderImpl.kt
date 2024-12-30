package com.example.moviedocs.utils.download

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

class DownloaderImpl(context: Context) : Downloader {

  private val downloadManager: DownloadManager =
    context.getSystemService(DownloadManager::class.java)

  override fun downloadFile(url: String, filename: String, mimeType: String): Long {
    val request: DownloadManager.Request = DownloadManager.Request(url.toUri())
      .setTitle(filename)
      .setMimeType(mimeType)
      .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
      .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
      .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    return downloadManager.enqueue(request)
  }
}