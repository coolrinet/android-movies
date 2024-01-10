package com.coolrinet.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolrinet.movies.data.MovieRepository
import com.coolrinet.movies.data.network.MovieSearchItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MovieSearchListViewModelFactory::class)
class MovieSearchListViewModel @AssistedInject constructor(
    private val movieRepository: MovieRepository,
    @Assisted val searchQuery: String,
) : ViewModel() {
    private val _movieSearchList: MutableStateFlow<List<MovieSearchItem>> = MutableStateFlow(
        emptyList()
    )
    val movieSearchList: StateFlow<List<MovieSearchItem>> = _movieSearchList.asStateFlow()

    init {
        viewModelScope.launch {
            _movieSearchList.value = movieRepository.searchMovies(searchQuery)
        }
    }
}

@AssistedFactory
interface MovieSearchListViewModelFactory {
    fun create(searchQuery: String): MovieSearchListViewModel
}