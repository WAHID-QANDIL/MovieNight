package org.wahid.movienight.domain.usecase

import org.wahid.movienight.domain.model.Movie
import org.wahid.movienight.domain.repository.MovieRepository
import javax.inject.Inject

class IsFavoriteMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movie: Movie) = repository.isFavoriteMovie(movie = movie)
}