package org.wahid.movienight.presentation.screen.home

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.wahid.movienight.domain.model.Movie

data class HomeUiState(
    val movies: Flow<PagingData<Movie>> = emptyFlow(),
    val trendingMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val isLoading: Boolean = false,
    val error: String? = null
)