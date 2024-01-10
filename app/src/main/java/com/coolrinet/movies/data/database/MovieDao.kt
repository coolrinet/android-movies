package com.coolrinet.movies.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getMovies(): Flow<List<Movie>>

    @Delete
    suspend fun deleteMovies(vararg movies: Movie)

    @Insert
    suspend fun addMovie(movie: Movie)
}