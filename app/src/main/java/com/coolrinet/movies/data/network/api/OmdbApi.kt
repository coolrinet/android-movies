package com.coolrinet.movies.data.network.api

import com.coolrinet.movies.data.network.OmdbResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET(".")
    suspend fun searchMovies(@Query("s") query: String): OmdbResponse
}