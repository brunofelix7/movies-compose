package dev.brunofelix.movies.core.domain.data_source

import dev.brunofelix.movies.core.data.remote.dto.MovieDto
import dev.brunofelix.movies.core.data.remote.dto.ResultDto
import dev.brunofelix.movies.core.data.remote.paging.MoviePopularPagingSource
import dev.brunofelix.movies.core.data.remote.paging.MovieUpcomingPagingSource
import retrofit2.Response

interface MovieRemoteDataSource {
    fun getPopularPagingSource(): MoviePopularPagingSource

    fun getUpcomingPagingSource(): MovieUpcomingPagingSource

    suspend fun getPopular(page: Int): Response<MovieDto>

    suspend fun getUpcoming(page: Int): Response<MovieDto>

    suspend fun getDetails(id: Long): Response<ResultDto>
}