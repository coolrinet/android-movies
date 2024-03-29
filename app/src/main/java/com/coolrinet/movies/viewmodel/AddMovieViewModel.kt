package com.coolrinet.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolrinet.movies.data.MovieRepository
import com.coolrinet.movies.data.database.Movie
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = AddMovieViewModelFactory::class)
class AddMovieViewModel @AssistedInject constructor(
    private val movieRepository: MovieRepository,
    @Assisted private val movieTitle: String? = null,
) : ViewModel() {
    private val _movie: MutableStateFlow<Movie> = MutableStateFlow(
        Movie(title = "", year = "")
    )
    val movie: StateFlow<Movie> = _movie.asStateFlow()

    init {
        if (movieTitle != null) {
            viewModelScope.launch {
                val selectedMovie = movieRepository.getMovieByTitle(movieTitle)
                updateMovie { movie ->
                    movie.copy(
                        title = selectedMovie.title,
                        year = selectedMovie.year,
                        posterUrl = selectedMovie.poster
                    )
                }
            }
        }
    }

    fun updateMovie(onUpdate: (Movie) -> Movie) {
        _movie.update {
            onUpdate(it)
        }
    }

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.addMovie(movie)
        }
    }
}

@AssistedFactory
interface AddMovieViewModelFactory {
    fun create(selectedMovie: String? = null): AddMovieViewModel
}