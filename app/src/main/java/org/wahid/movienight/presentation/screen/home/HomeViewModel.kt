package org.wahid.movienight.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.wahid.movienight.domain.model.Movie
import org.wahid.movienight.domain.usecase.GetFiveTrendingMoviesUseCase
import org.wahid.movienight.domain.usecase.GetMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getMoviesUseCase: GetMoviesUseCase,
    fiveTrendingMoviesUseCase: GetFiveTrendingMoviesUseCase,

    ) : ViewModel() {

    val uiState = MutableStateFlow(HomeUiState())

    private val movies =
        getMoviesUseCase(emptyMap())


    init {
        viewModelScope.launch {
            try {
                var trendingMovies: List<Movie> = fiveTrendingMoviesUseCase()
                uiState.update {
                    it.copy(
                        movies = movies,
                        trendingMovies = trendingMovies
                    )
                }
            } catch (e: Exception) {
                uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }

        }
    }


}