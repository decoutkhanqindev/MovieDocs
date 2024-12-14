package com.example.moviedocs.utils

import android.annotation.SuppressLint
import com.example.moviedocs.domain.model.country.CountryItemModel
import com.example.moviedocs.domain.model.language.LanguageItemModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
fun String.formatDate(): String {
  if (this.isBlank()) {
    return "Unknown date"
  }
  val inputFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
  inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Set input time zone to UTC
  val outputFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
  val date: Date? = inputFormat.parse(this)
  return if (date != null) outputFormat.format(date) else "Unknown date"
}

fun String.toLanguageName(languageList: List<LanguageItemModel>): String {
  if (this.isBlank()) {
    return "Unknown language"
  }
  val language: LanguageItemModel? = languageList.find { it.iso6391 == this }
  return language?.englishName ?: "Unknown language"
}

fun String.toCountryName(countryList: List<CountryItemModel>): String {
  if (this.isBlank()) {
    return "Unknown country"
  }
  val country: CountryItemModel? = countryList.find { it.iso31661 == this }
  return country?.englishName ?: "Unknown country"
}
