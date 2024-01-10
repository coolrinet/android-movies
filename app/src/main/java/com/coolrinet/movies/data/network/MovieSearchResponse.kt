package com.coolrinet.movies.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieSearchResponse(
    @Json(name = "Search") val searchResults: List<MovieSearchItem>,
)
