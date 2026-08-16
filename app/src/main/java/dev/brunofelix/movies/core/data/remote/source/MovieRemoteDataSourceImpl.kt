package dev.brunofelix.movies.core.data.remote.source

import androidx.paging.PagingSource
import dev.brunofelix.movies.core.data.remote.MovieService
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.mapper.toDomainList
import dev.brunofelix.movies.core.data.remote.paging.BasePagingSource
import dev.brunofelix.movies.core.data.util.extension.mapOrThrow
import dev.brunofelix.movies.core.data.util.extension.toRemoteException
import dev.brunofelix.movies.core.domain.model.Movie
import javax.inject.Inject

/**
 * Implementation of [MovieRemoteDataSource] using [MovieService].
 * @property service The Retrofit service for movie API calls.
 */
class MovieRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : MovieRemoteDataSource {

    override fun getPopularPagingSource(): PagingSource<Int, Movie> {
        return BasePagingSource { page ->
            runCatching {
                service.getPopulars(page).mapOrThrow { it.toDomainList() }
            }.recoverCatching { throw it.toRemoteException() }
        }
    }

    override fun getUpcomingPagingSource(): PagingSource<Int, Movie> {
        return BasePagingSource { page ->
            runCatching {
                service.getUpcoming(page).mapOrThrow { it.toDomainList() }
            }.recoverCatching { throw it.toRemoteException() }
        }
    }

    override fun getTopRatedPagingSource(): PagingSource<Int, Movie> {
        return BasePagingSource { page ->
            runCatching {
                service.getTopRated(page).mapOrThrow { it.toDomainList() }
            }.recoverCatching { throw it.toRemoteException() }
        }
    }

    override fun search(query: String, page: Int): PagingSource<Int, Movie> {
        return BasePagingSource { page ->
            runCatching {
                service.search(query, page).mapOrThrow { it.toDomainList() }
            }.recoverCatching { throw it.toRemoteException() }
        }
    }

    override suspend fun getDetails(id: Long): Result<Movie> {
        return runCatching {
            service.getDetails(id).mapOrThrow { it.toDomain() }
        }.recoverCatching { throw it.toRemoteException() }
    }
}
