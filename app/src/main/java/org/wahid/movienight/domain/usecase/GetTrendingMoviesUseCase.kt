package org.wahid.movienight.domain.usecase

import org.wahid.movienight.domain.repository.MovieRepository
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
     operator fun invoke() = repository.getTrendingMovies()
}