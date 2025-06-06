package org.wahid.movienight.data.local.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(
    tableName ="movie",
    indices = [
        Index("id"),
        Index("title"),
    ]
)
data class MovieDb(
    @PrimaryKey(autoGenerate = false)           val id: Int,
    @ColumnInfo(name = "adult")                 val adult: Boolean,
    @ColumnInfo(name = "backdrop_path")         val backdropPath: String?,
    @ColumnInfo(name = "original_language")     val originalLanguage: String,
    @ColumnInfo(name = "original_title")        val originalTitle: String,
    @ColumnInfo(name = "overview")              val overview: String,
    @ColumnInfo(name = "popularity")            val popularity: Double,
    @ColumnInfo(name = "poster_path")           val posterPath: String,
    @ColumnInfo(name = "release_date")          val releaseDate: String,
    @ColumnInfo(name = "title")                 val title: String,
    @ColumnInfo(name = "video")                 val video: Boolean,
    @ColumnInfo(name = "vote_average")          val voteAverage: Double,
    @ColumnInfo(name = "vote_count")            val voteCount: Int,
    @ColumnInfo(name = "query")                 val query: Map<String, String>,
    @ColumnInfo(name = "updated")               val updated: Instant,
    @ColumnInfo(name = "favorite")              val favorite: Boolean = false
)