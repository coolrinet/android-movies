package com.coolrinet.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [Movie::class]
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}