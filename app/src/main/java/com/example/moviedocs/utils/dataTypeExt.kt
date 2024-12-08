package com.example.moviedocs.utils

import android.annotation.SuppressLint
import com.example.moviedocs.domain.model.country.CountryItemModel
import com.example.moviedocs.domain.model.language.LanguageItemModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
fun String.formatTimestamp(): String {
  if (this.isEmpty()) {
    return "00/00/0000"
  }
  val inputFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
  inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Set input time zone to UTC
  val outputFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
  val date: Date? = inputFormat.parse(this)
  return if (date != null) outputFormat.format(date) else "00/00/000"
}

fun String.toLanguageName(languageList: List<LanguageItemModel>): String {
  val languageCode: String = this
  val language: LanguageItemModel? = languageList.find { it.iso6391 == languageCode }
  return language!!.englishName
}

fun String.toCountryName(countryList: List<CountryItemModel>): String {
  val countryCode: String = this
  val country: CountryItemModel? = countryList.find { it.iso31661 == countryCode }
  return country!!.englishName
}

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