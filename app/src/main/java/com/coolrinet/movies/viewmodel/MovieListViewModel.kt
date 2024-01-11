package com.coolrinet.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolrinet.movies.data.MovieRepository
import com.coolrinet.movies.data.database.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _movies: MutableStateFlow<List<Movie>> = MutableStateFlow(
        emptyList()
    )
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    private val moviesToDelete: MutableList<Movie> = mutableListOf()

    init {
        viewModelScope.launch {
            movieRepository.getMovies().collect {
                _movies.value = it
            }
        }
    }

    fun changeMovieDeletionStatus(movie: Movie) {
        if (movie in moviesToDelete) {
            moviesToDelete.remove(movie)
        } else {
            moviesToDelete.add(movie)
        }
    }

    fun deleteMovies(vararg movies: Movie) {
        viewModelScope.launch {
            movieRepository.deleteMovies(*movies)
        }
    }
}