package dev.brunofelix.movies.core.data.remote.source

import androidx.paging.PagingSource
import dev.brunofelix.movies.core.domain.model.Movie

interface MovieRemoteDataSource {
    fun getPopularPagingSource(): PagingSource<Int, Movie>

    fun getUpcomingPagingSource(): PagingSource<Int, Movie>

    fun getTopRatedPagingSource(): PagingSource<Int, Movie>

    suspend fun getPopular(page: Int): Result<List<Movie>>

    suspend fun getUpcoming(page: Int): Result<List<Movie>>

    suspend fun getTopRated(page: Int): Result<List<Movie>>

    suspend fun getDetails(id: Long): Result<Movie>
}

