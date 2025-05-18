package org.wahid.movienight.data.remote.api_service

import org.wahid.movienight.data.remote.model.RemoteMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MovieApiService {
    @GET("discover/movie")
    suspend fun getMovies(
        @QueryMap query: Map<String, String>,
        @Query("page") page: Int
    ): RemoteMovieResponse
}