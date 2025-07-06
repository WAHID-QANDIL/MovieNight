package org.wahid.movienight.presentation.screen.details

import org.wahid.movienight.domain.model.Movie

data class DetailsUiState(
    val movie: Movie? = null,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
