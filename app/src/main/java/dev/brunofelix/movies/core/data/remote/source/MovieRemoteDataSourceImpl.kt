package dev.brunofelix.movies.core.data.remote.source

import dev.brunofelix.movies.core.data.remote.MovieService
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.mapper.toDomainList
import dev.brunofelix.movies.core.data.util.BaseRemoteDataSource
import dev.brunofelix.movies.core.domain.model.Movie
import javax.inject.Inject

/**
 * Implementation of [MovieRemoteDataSource] using [MovieService].
 * @property service The Retrofit service for movie API calls.
 */
class MovieRemoteDataSourceImpl @Inject constructor(
    service: MovieService
) : BaseRemoteDataSource<MovieService>(service), MovieRemoteDataSource {

    override suspend fun getPopulars(page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { getPopulars(page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun getUpcoming(page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { getUpcoming(page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun getTopRated(page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { getTopRated(page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun search(query: String, page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { search(query, page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun getDetails(id: Long): Result<Movie> {
        return safeApiCall(
            call = { getDetails(id) },
            transform = { it.toDomain() }
        )
    }
}
