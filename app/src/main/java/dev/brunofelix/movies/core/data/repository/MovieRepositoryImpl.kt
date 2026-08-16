package dev.brunofelix.movies.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.data.local.source.MovieLocalDataSource
import dev.brunofelix.movies.core.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.domain.util.toResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of [MovieRepository].
 *
 * @property remoteDataSource The source for remote movie data.
 * @property localDataSource The source for local movie persistence.
 */
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun save(movie: Movie) {
        localDataSource.insert(movie)
    }

    override suspend fun delete(movie: Movie) {
        localDataSource.delete(movie)
    }

    override suspend fun isFavorite(id: Long): Boolean {
        return localDataSource.getById(id) != null
    }

    override suspend fun getDetails(id: Long): Resource<Movie> {
        return remoteDataSource.getDetails(id).toResource()
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getAll().map { entityList ->
            entityList.map { it }
        }
    }

    override fun search(query: String, pagingConfig: PagingConfig): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                remoteDataSource.search(query)
            }
        ).flow
    }

    override fun getPopularMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                remoteDataSource.getPopularPagingSource()
            }
        ).flow
    }

    override fun getUpcomingMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                remoteDataSource.getUpcomingPagingSource()
            }
        ).flow
    }

    override fun getTopRatedMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                remoteDataSource.getTopRatedPagingSource()
            }
        ).flow
    }
}
