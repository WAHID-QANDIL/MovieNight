package org.wahid.movienight.data.remote.api_service

import org.wahid.movienight.data.remote.model.MovieDto
import retrofit2.http.GET

interface MovieApiService {
    @GET("discover/movie")
    suspend fun getMovies(
        query: Map<String, String>,
        page: Int
    ): MovieDto
}