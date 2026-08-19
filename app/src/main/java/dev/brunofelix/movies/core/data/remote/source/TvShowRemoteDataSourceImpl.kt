package dev.brunofelix.movies.core.data.remote.source

import dev.brunofelix.movies.core.data.remote.TvShowService
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.mapper.toDomainList
import dev.brunofelix.movies.core.data.util.BaseRemoteDataSource
import dev.brunofelix.movies.core.domain.model.TvShow
import javax.inject.Inject

/**
 * Implementation of [TvShowRemoteDataSource] using [TvShowService].
 * @property service The Retrofit service for TV show API calls.
 */
class TvShowRemoteDataSourceImpl @Inject constructor(
    service: TvShowService
) : BaseRemoteDataSource<TvShowService>(service), TvShowRemoteDataSource {

    override suspend fun getPopulars(page: Int): Result<List<TvShow>> {
        return safeApiCall(
            call = { getPopulars(page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun getTopRated(page: Int): Result<List<TvShow>> {
        return safeApiCall(
            call = { getTopRated(page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun search(query: String, page: Int): Result<List<TvShow>> {
        return safeApiCall(
            call = { search(query, page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun getDetails(id: Long): Result<TvShow> {
        return safeApiCall(
            call = { getDetails(id) },
            transform = { it.toDomain() }
        )
    }
}
