package org.wahid.movienight.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteMovieResponse(
    @SerialName("page")             val page: Int,
    @SerialName("results")          val results: List<MovieModel>,
    @SerialName("total_pages")      val totalPages: Int,
    @SerialName("total_results")    val totalResults: Int
)