package dev.brunofelix.movies.core.data.remote.source

import androidx.paging.PagingSource
import dev.brunofelix.movies.core.domain.model.TvShow

interface TvShowRemoteDataSource {
    fun getPopularPagingSource(): PagingSource<Int, TvShow>

    fun getTopRatedPagingSource(): PagingSource<Int, TvShow>

    suspend fun getPopular(page: Int): Result<List<TvShow>>

    suspend fun getTopRated(page: Int): Result<List<TvShow>>

    suspend fun getDetails(id: Long): Result<TvShow>
}

