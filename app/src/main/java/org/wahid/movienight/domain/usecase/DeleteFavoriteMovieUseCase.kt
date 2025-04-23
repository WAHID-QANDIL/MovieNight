package org.wahid.movienight.domain.usecase

import org.wahid.movienight.domain.model.Movie
import org.wahid.movienight.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteFavoriteMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) = repository.deleteFavoriteMovie(movie = movie)
}