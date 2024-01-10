package com.coolrinet.movies.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OmdbResponse(
    val movies: MovieSearchResponse,
)
