package org.wahid.movienight.mapper

import org.wahid.movienight.data.remote.model.MovieModel
import org.wahid.movienight.domain.model.Movie

fun MovieModel.toDomain(): Movie {
    return with(this) {
        Movie(
            id = id,
            adult = adult,
            backdropPath = backdropPath,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount,
        )
    }
}