package org.wahid.movienight.domain.usecase

import org.wahid.movienight.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMovie @Inject constructor(
    private val repository: MovieRepository,
) {
    operator fun invoke(query: String) = repository.searchMovies(query = query)
}