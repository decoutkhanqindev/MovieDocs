package com.example.moviedocs.data.remote.response.language

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class LanguageItemResponse(
    @Json(name = "iso_639_1")
    val iso6391: String, // kw
    @Json(name = "english_name")
    val englishName: String?, // Cornish
    @Json(name = "name")
    val name: String?,
)
