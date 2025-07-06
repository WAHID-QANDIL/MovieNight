package org.wahid.movienight.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.wahid.movienight.data.local.db.MovieDatabase
import org.wahid.movienight.data.local.db.model.FavoriteMovieDb
import org.wahid.movienight.data.local.db.model.MovieDb
import org.wahid.movienight.data.mediator.RemoteMovieMediator
import org.wahid.movienight.data.remote.api_service.MovieApiService
import org.wahid.movienight.domain.model.Movie
import org.wahid.movienight.domain.repository.MovieRepository
import org.wahid.movienight.mapper.toDomainModule
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl @Inject constructor(
    private val database: MovieDatabase,
    private val apiService: MovieApiService,
) : MovieRepository {

    private val movieDao = database.movieDao()
    private val favoriteMovieDao = database.favoriteMovieDao()


    override fun getMovies(query: Map<String, String>): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = RemoteMovieMediator(
                query = query,
                apiService = apiService,
                database = database,
                cacheTimeOut = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
            ),
        ) {
            movieDao.getMovies(query = query)
        }.flow.map { pagingData: PagingData<MovieDb> ->
            pagingData.map { movieDb ->
                movieDb.toDomainModule()
            }
        }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getFavoriteMovies().map { listMovieDb ->
            listMovieDb.map { movieDb ->
                movieDb.toDomainModule()
            }
        }
    }

    override fun searchMovies(query: String): Flow<List<Movie>> {
        return movieDao.searchByTitle(query = query).map { listMovieDb ->
            listMovieDb.map { movieDb -> movieDb.toDomainModule() }
        }
    }

    override suspend fun saveFavoriteMovie(movie: Movie) {
        favoriteMovieDao.upsert(FavoriteMovieDb(movieId = movie.id))
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        favoriteMovieDao.delete(FavoriteMovieDb(movieId = movie.id))
    }

    override fun isFavoriteMovie(movie: Movie): Flow<Boolean> {
        return favoriteMovieDao.isFavoriteMovie(id = movie.id)
    }

    override suspend fun get5THTrendingMovies(): Flow<Movie> {
        val result = apiService.get5THTrendingMovies().results
        val firstFiveMovies = result.subList(0, 5)
        return flow { firstFiveMovies }
    }
}