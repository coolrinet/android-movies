package com.coolrinet.movies.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val year: Int,
    val posterUrl: String,
)
