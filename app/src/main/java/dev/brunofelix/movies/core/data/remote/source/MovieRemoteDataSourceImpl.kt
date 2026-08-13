package dev.brunofelix.movies.core.data.remote.source

import dev.brunofelix.movies.core.data.remote.MovieService
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.paging.MoviePopularPagingSource
import dev.brunofelix.movies.core.data.remote.paging.MovieUpcomingPagingSource
import dev.brunofelix.movies.core.data.remote.source.base.RemoteDataSource
import dev.brunofelix.movies.core.domain.model.Movie
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    api: MovieService
) : RemoteDataSource<MovieService>(api), MovieRemoteDataSource {

    override fun getPopularPagingSource() = MoviePopularPagingSource(this)

    override fun getUpcomingPagingSource() = MovieUpcomingPagingSource(this)

    override suspend fun getPopular(page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { getPopulars(page) },
            transform = { it.results?.map { result -> result.toDomain() } ?: emptyList() }
        )
    }

    override suspend fun getUpcoming(page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { getUpcoming(page) },
            transform = { it.results?.map { result -> result.toDomain() } ?: emptyList() }
        )
    }

    override suspend fun getDetails(id: Long): Result<Movie> {
        return safeApiCall(
            call = { getDetails(id) },
            transform = { it.toDomain() }
        )
    }
}
