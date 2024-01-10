package com.coolrinet.movies.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieSearchItem(
    @Json(name = "Title") val title: String,
    @Json(name = "Year") val year: Int,
    @Json(name = "Poster") val poster: String,
    @Json(name = "Type") val type: String,
)