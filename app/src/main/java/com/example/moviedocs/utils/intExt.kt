package com.example.moviedocs.utils

import android.icu.text.DecimalFormat

fun Int.convertMinutesToHoursAndMinutes(): String {
  val minutes: Int = this
  val hours: Int = minutes / 60
  val remainingMinutes: Int = minutes % 60
  return "${hours}h ${remainingMinutes}m"
}

fun Int.formatTotalResult(): String {
  val totalResult: Int = this
  return "($totalResult):"
}

fun Int.formatDollar(): String {
  val dollar: Double = this.toDouble()
  val formatDecimal: DecimalFormat = DecimalFormat("###,###,##0.00")
  return "$${formatDecimal.format(dollar)}"
}