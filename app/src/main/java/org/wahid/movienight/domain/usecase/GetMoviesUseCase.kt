package org.wahid.movienight.domain.usecase

import org.wahid.movienight.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    operator fun invoke(query: Map<String, String>) = repository.getMovies(query = query)
}