package com.coolrinet.movies.data

import com.coolrinet.movies.data.database.Movie
import com.coolrinet.movies.data.database.MovieDao
import com.coolrinet.movies.data.network.api.OmdbApi

class MovieRepositoryImpl(
    private val omdbApi: OmdbApi,
    private val movieDao: MovieDao,
) : MovieRepository {
    override fun getMovies() = movieDao.getMovies()

    override suspend fun deleteMovies(vararg movies: Movie) {
        movieDao.deleteMovies(*movies)
    }

    override suspend fun addMovie(movie: Movie) {
        movieDao.addMovie(movie)
    }

    override suspend fun searchMovies(query: String) =
        omdbApi.searchMovies(query).searchResults

    override suspend fun getMovieByTitle(title: String) =
        omdbApi.getMovieByTitle(title)
}