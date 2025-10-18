package dev.brunofelix.movies.core.data.source

import dev.brunofelix.movies.core.data.api.dto.MovieDto
import dev.brunofelix.movies.core.data.api.dto.ResultDto
import dev.brunofelix.movies.core.data.api.paging.MoviePopularPagingSource
import dev.brunofelix.movies.core.data.api.paging.MovieUpcomingPagingSource
import retrofit2.Response

interface MovieRemoteDataSource {
    fun getPopularPagingSource(): MoviePopularPagingSource

    fun getUpcomingPagingSource(): MovieUpcomingPagingSource

    suspend fun getPopular(page: Int): Response<MovieDto>

    suspend fun getUpcoming(page: Int): Response<MovieDto>

    suspend fun getDetails(id: Long): Response<ResultDto>
}