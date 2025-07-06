package org.wahid.movienight.domain.usecase

import org.wahid.movienight.domain.model.Movie
import org.wahid.movienight.domain.repository.MovieRepository
import javax.inject.Inject

class GetFiveTrendingMoviesUseCase @Inject constructor(
    val repository: MovieRepository,
) {
    suspend operator fun invoke(): List<Movie> {
        return repository.get5THTrendingMovies()
    }
}