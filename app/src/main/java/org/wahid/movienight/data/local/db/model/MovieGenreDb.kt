package org.wahid.movienight.data.local.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
    tableName = "movie_genre",
    indices = [
        Index("movie_id"),
        Index("genre_id")
    ],
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = MovieDb::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"]
        )
    ],

    primaryKeys = ["movie_id", "genre_id"]
)
data class MovieGenreDb(

    @ColumnInfo(name = "movie_id")  val movieId: Int,
    @ColumnInfo(name = "genre_id")  val genreId: Int
)