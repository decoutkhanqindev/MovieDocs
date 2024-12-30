package com.example.moviedocs.utils.download

interface Downloader {
  fun downloadFile(url: String, filename: String, mimeType: String): Long
}