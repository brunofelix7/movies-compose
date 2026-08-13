package dev.brunofelix.movies.core.data.remote.source

import dev.brunofelix.movies.core.domain.model.TvShow

interface TvShowRemoteDataSource {
    suspend fun getPopular(page: Int): Result<List<TvShow>>

    suspend fun getTopRated(page: Int): Result<List<TvShow>>

    suspend fun getDetails(id: Long): Result<TvShow>
}
