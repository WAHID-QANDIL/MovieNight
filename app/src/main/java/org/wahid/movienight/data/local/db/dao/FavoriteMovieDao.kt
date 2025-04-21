package org.wahid.movienight.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.wahid.movienight.data.local.db.model.FavoriteMovieDb

@Dao
abstract class FavoriteMovieDao : BaseDao<FavoriteMovieDb>() {
    @Query(
        """
        SELECT EXISTS(SELECT 1
                      FROM favorite_movie
                      WHERE movie_id = :id)
        """
    )
    abstract fun isFavoriteMovie(id: Int): Flow<Boolean>
}