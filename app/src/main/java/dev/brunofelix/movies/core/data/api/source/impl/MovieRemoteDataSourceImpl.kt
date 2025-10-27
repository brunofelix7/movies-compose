package dev.brunofelix.movies.core.data.api.source.impl

import dev.brunofelix.movies.core.data.api.MovieApi
import dev.brunofelix.movies.core.data.api.paging.MoviePopularPagingSource
import dev.brunofelix.movies.core.data.api.paging.MovieUpcomingPagingSource
import dev.brunofelix.movies.core.data.api.source.RemoteDataSource
import dev.brunofelix.movies.core.data.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.domain.model.Movie
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    api: MovieApi
) : RemoteDataSource<MovieApi>(api), MovieRemoteDataSource {

    override fun getPopularPagingSource() = MoviePopularPagingSource(this)

    override fun getUpcomingPagingSource() = MovieUpcomingPagingSource(this)

    override suspend fun getPopular(page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { getPopulars(page) },
            transform = { it.results?.map { result -> result.toModel() } ?: emptyList() }
        )
    }

    override suspend fun getUpcoming(page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { getUpcoming(page) },
            transform = { it.results?.map { result -> result.toModel() } ?: emptyList() }
        )
    }

    override suspend fun getDetails(id: Long): Result<Movie> {
        return safeApiCall(
            call = { getDetails(id) },
            transform = { it.toModel() }
        )
    }
}