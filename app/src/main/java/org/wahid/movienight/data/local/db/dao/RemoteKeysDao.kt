package org.wahid.movienight.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import org.wahid.movienight.data.local.db.model.RemoteKeyDb
@Dao
abstract class RemoteKeysDao: BaseDao<RemoteKeyDb>() {
    @Query(
        """
        SELECT * FROM remote_key
        WHERE `query` = :query
        """
    )
    abstract fun remoteKeyByQuery(query: Map<String, String>): RemoteKeyDb?

    @Query(
        """
        DELETE FROM remote_key
        WHERE `query` = :query
        """
    )
    abstract fun deleteByQuery(query: Map<String, String>)
}