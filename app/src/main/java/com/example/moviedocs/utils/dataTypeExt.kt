package com.example.moviedocs.utils

import android.annotation.SuppressLint
import com.example.moviedocs.domain.model.moviedetail.country.CountryItemModel
import com.example.moviedocs.domain.model.moviedetail.language.LanguageItemModel
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

fun String.toLanguageName(languageList: List<LanguageItemModel>): String {
  val languageCode = this
  val language = languageList.find { it.iso6391 == languageCode }
  return language!!.englishName
}

fun String.toCountryName(countryList: List<CountryItemModel>): String {
  val countryCode = this
  val country = countryList.find { it.iso31661 == countryCode }
  return country!!.englishName
}

fun Int.convertMinutesToHoursAndMinutes(): String {
  val minutes = this
  val hours = minutes / 60
  val remainingMinutes = minutes % 60
  return "${hours}h ${remainingMinutes}m"
}