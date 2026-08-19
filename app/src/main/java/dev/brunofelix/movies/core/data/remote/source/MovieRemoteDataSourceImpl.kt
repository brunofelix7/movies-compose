package dev.brunofelix.movies.core.data.remote.source

import androidx.paging.PagingSource
import dev.brunofelix.movies.core.data.remote.MovieService
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.mapper.toDomainList
import dev.brunofelix.movies.core.data.remote.paging.BasePagingSource
import dev.brunofelix.movies.core.data.util.RemoteDataSource
import dev.brunofelix.movies.core.data.util.extension.mapOrThrow
import dev.brunofelix.movies.core.data.util.extension.toRemoteException
import dev.brunofelix.movies.core.domain.model.Movie
import javax.inject.Inject

/**
 * Implementation of [MovieRemoteDataSource] using [MovieService].
 * @property service The Retrofit service for movie API calls.
 */
class MovieRemoteDataSourceImpl @Inject constructor(
    service: MovieService
) : RemoteDataSource<MovieService>(service), MovieRemoteDataSource {

    override fun getPopularPagingSource(): PagingSource<Int, Movie> {
        return BasePagingSource { page ->
            safeApiCall(
                call = { getPopulars(page) },
                transform = { it.toDomainList() }
            ).recoverCatching { throw it.toRemoteException() }
        }
    }

    override fun getUpcomingPagingSource(): PagingSource<Int, Movie> {
        return BasePagingSource { page ->
            safeApiCall(
                call = { getUpcoming(page) },
                transform = { it.toDomainList() }
            ).recoverCatching { throw it.toRemoteException() }
        }
    }

    override fun getTopRatedPagingSource(): PagingSource<Int, Movie> {
        return BasePagingSource { page ->
            safeApiCall(
                call = { getTopRated(page) },
                transform = { it.toDomainList() }
            ).recoverCatching { throw it.toRemoteException() }
        }
    }

    override suspend fun search(query: String, page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { search(query, page) },
            transform = { it.toDomainList() }
        ).recoverCatching { throw it.toRemoteException() }
    }

    override suspend fun getDetails(id: Long): Result<Movie> {
        return safeApiCall(
            call = { getDetails(id) },
            transform = { it.toDomain() }
        ).recoverCatching { throw it.toRemoteException() }
    }
}
