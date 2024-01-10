package com.coolrinet.movies.data

import com.coolrinet.movies.data.database.Movie
import com.coolrinet.movies.data.network.MovieSearchItem
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<List<Movie>>

    suspend fun deleteMovies(vararg movies: Movie)

    suspend fun addMovie(movie: Movie)

    suspend fun searchMovies(query: String): List<MovieSearchItem>
}