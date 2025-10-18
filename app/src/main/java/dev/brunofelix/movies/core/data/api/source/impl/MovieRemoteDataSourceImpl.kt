package dev.brunofelix.movies.core.data.api.source.impl

import dev.brunofelix.movies.core.data.api.MovieApi
import dev.brunofelix.movies.core.data.api.paging.MoviePopularPagingSource
import dev.brunofelix.movies.core.data.api.paging.MovieUpcomingPagingSource
import dev.brunofelix.movies.core.data.source.MovieRemoteDataSource
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: MovieApi
) : MovieRemoteDataSource {

    override fun getPopularPagingSource() = MoviePopularPagingSource(this)

    override fun getUpcomingPagingSource() = MovieUpcomingPagingSource(this)

    override suspend fun getPopular(page: Int) = api.getPopulars(page)

    override suspend fun getUpcoming(page: Int) = api.getUpcoming(page)

    override suspend fun getDetails(id: Long) = api.getDetails(id)
}