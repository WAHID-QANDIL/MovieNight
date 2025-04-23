package org.wahid.movienight.mapper

import kotlinx.datetime.Clock
import org.wahid.movienight.data.local.db.model.MovieDb
import org.wahid.movienight.data.remote.model.MovieDto

fun MovieDto.toDatabase(query: Map<String, String>): MovieDb{
    return with (this){
        MovieDb(
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
            updated = Clock.System.now(),
            query = query
        )
    }
}