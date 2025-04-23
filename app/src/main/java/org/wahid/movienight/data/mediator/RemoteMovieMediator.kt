package org.wahid.movienight.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil3.network.HttpException
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import org.wahid.movienight.data.local.db.MovieDatabase
import org.wahid.movienight.data.remote.api_service.MovieApiService
import kotlinx.datetime.until
import org.wahid.movienight.data.local.db.model.MovieDb
import org.wahid.movienight.data.local.db.model.MovieGenreDb
import org.wahid.movienight.data.local.db.model.RemoteKeyDb
import org.wahid.movienight.mapper.toDatabase

const val LAST_PAGE = -2

@OptIn(ExperimentalPagingApi::class)
class RemoteMovieMediator(
    private val query: Map<String, String>,
    private val apiService: MovieApiService,
    private val database: MovieDatabase,
    private val cacheTimeOut: Long,
) : RemoteMediator<Int, MovieDb>() {

    private val movieDao = database.movieDao()
    private val movieGenreDao = database.movieGenreDao()
    private val remoteKeyDao = database.remoteKeyDao()


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieDb>,
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND ->return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKeyByQuery(query = query)
                    }
                    if (remoteKey?.nextPage == LAST_PAGE){
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey?.nextPage?.plus(1)
                }
            }
            val response = apiService.getMovies(query = query, page = loadKey ?: 1)

            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    remoteKeyDao.deleteByQuery(query = query)
                    movieDao.deleteByQuery(query = query)
                }
                remoteKeyDao.upsert(
                    RemoteKeyDb(
                        query = query,
                        nextPage = if (response.results.isEmpty())LAST_PAGE else (loadKey?:1)
                    )
                )

                response.results.forEach { remoteMovie ->
                    movieDao.upsert(remoteMovie.toDatabase(query = query))
                    movieGenreDao.upsert(
                        remoteMovie.genreIds.map { genreId->
                            MovieGenreDb(
                                movieId = remoteMovie.id,
                                genreId = genreId,
                            )
                        }
                    )
                }
            }
            MediatorResult.Success(endOfPaginationReached = true)
        }catch (
            e: Exception
        ){
            MediatorResult.Error(e)
        }catch (e: HttpException)
        {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val now = Clock.System.now()
        val period = movieDao.lastUpdated()?.until(
            other = now,
            unit = DateTimeUnit.HOUR,
            timeZone = TimeZone.currentSystemDefault()
        ) ?: cacheTimeOut
        return if (period >= cacheTimeOut) InitializeAction.SKIP_INITIAL_REFRESH else InitializeAction.LAUNCH_INITIAL_REFRESH
    }
}