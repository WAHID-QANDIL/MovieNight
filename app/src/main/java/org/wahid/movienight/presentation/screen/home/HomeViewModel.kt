package org.wahid.movienight.presentation.screen.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.wahid.movienight.domain.usecase.GetMoviesUseCase
import org.wahid.movienight.domain.usecase.GetTrendingMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getMoviesUseCase: GetMoviesUseCase,
    getTrendingMoviesUseCase: GetTrendingMoviesUseCase,

    ) : ViewModel() {

    val uiState = MutableStateFlow(HomeUiState())

    private val movies =
        getMoviesUseCase(emptyMap())

    private val trendingMovies =
        getTrendingMoviesUseCase()


    init {
        uiState.update {
            it.copy(
                movies = movies,
                trendingMovies = trendingMovies
            )
        }
    }


}