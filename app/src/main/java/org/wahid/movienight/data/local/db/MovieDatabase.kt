package org.wahid.movienight.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.wahid.movienight.data.local.db.converter.InstantConverter
import org.wahid.movienight.data.local.db.converter.QueryConverter
import org.wahid.movienight.data.local.db.dao.FavoriteMovieDao
import org.wahid.movienight.data.local.db.dao.MovieDao
import org.wahid.movienight.data.local.db.dao.MovieGenreDao
import org.wahid.movienight.data.local.db.dao.RemoteKeysDao
import org.wahid.movienight.data.local.db.model.FavoriteMovieDb
import org.wahid.movienight.data.local.db.model.MovieDb
import org.wahid.movienight.data.local.db.model.MovieGenreDb
import org.wahid.movienight.data.local.db.model.RemoteKeyDb

@Database(
    entities = [
        MovieDb::class,
        MovieGenreDb::class,
        RemoteKeyDb::class,
        FavoriteMovieDb::class
    ],
    version = 1,
    exportSchema = true
)

@TypeConverters(
    value = [
        InstantConverter::class,
        QueryConverter::class
    ]
)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeyDao(): RemoteKeysDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun movieGenreDao(): MovieGenreDao

    companion object {
        const val DATABASE_NAME = "movie_database"
    }
}