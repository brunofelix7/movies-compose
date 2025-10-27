package dev.brunofelix.movies.core.data.source

import dev.brunofelix.movies.core.data.api.paging.MoviePopularPagingSource
import dev.brunofelix.movies.core.data.api.paging.MovieUpcomingPagingSource
import dev.brunofelix.movies.core.domain.model.Movie

interface MovieRemoteDataSource {
    fun getPopularPagingSource(): MoviePopularPagingSource

    fun getUpcomingPagingSource(): MovieUpcomingPagingSource

    suspend fun getPopular(page: Int): Result<List<Movie>>

    suspend fun getUpcoming(page: Int): Result<List<Movie>>

    suspend fun getDetails(id: Long): Result<Movie>
}