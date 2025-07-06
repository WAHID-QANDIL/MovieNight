package org.wahid.movienight.presentation.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.wahid.movienight.domain.model.Movie
import org.wahid.movienight.domain.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState = _uiState.asStateFlow()

    private var currentMovie: Movie? = null

    init {
        // Movie will be passed via navigation arguments
        savedStateHandle.get<Int>("movieId")?.let { movieId ->
            // If needed, fetch additional details
        }
    }

    fun setMovie(movie: Movie) {
        currentMovie = movie
        _uiState.update { it.copy(movie = movie) }

        // Check if movie is favorite
        viewModelScope.launch {
            repository.isFavoriteMovie(movie).collect { isFavorite ->
                _uiState.update { it.copy(isFavorite = isFavorite) }
            }
        }
    }

    fun toggleFavorite() {
        currentMovie?.let { movie ->
            viewModelScope.launch {
                if (_uiState.value.isFavorite) {
                    repository.deleteFavoriteMovie(movie)
                } else {
                    repository.saveFavoriteMovie(movie)
                }
            }
        }
    }
}

