package org.wahid.movienight.mapper

import org.wahid.movienight.BuildConfig
import org.wahid.movienight.data.local.db.model.MovieDb
import org.wahid.movienight.domain.model.Movie

fun MovieDb.toDomainModule(): Movie{
  return with(this){
        Movie(
            id = id,
            adult = adult,
            backdropPath = BuildConfig.IMAGE_URL + backdropPath,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = BuildConfig.IMAGE_URL + posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount,
            query = query
        )
    }
}