package com.example.moviedocs.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
fun String.formatTimestamp(): String {
  val inputFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
  inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Set input time zone to UTC
  val outputFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
  val date: Date? = inputFormat.parse(this)
  return if (date != null) outputFormat.format(date) else "Invalid Date"
}