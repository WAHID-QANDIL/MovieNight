package org.wahid.movienight.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.wahid.movienight.domain.model.Movie

interface MovieRepository {
    fun getMovies(query: Map<String, String>): Flow<PagingData<Movie>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun searchMovies(query: String): Flow<List<Movie>>

    suspend fun saveFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)

    fun isFavoriteMovie(movie: Movie): Flow<Boolean>

    suspend fun get5THTrendingMovies(): Flow<Movie>
}