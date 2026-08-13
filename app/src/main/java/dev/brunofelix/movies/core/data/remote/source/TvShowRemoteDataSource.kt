package dev.brunofelix.movies.core.data.remote.source

import androidx.paging.PagingSource
import dev.brunofelix.movies.core.data.remote.TvShowService
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.mapper.toDomainList
import dev.brunofelix.movies.core.data.remote.paging.BasePagingSource
import dev.brunofelix.movies.core.data.util.extension.mapOrThrow
import dev.brunofelix.movies.core.data.util.extension.toRemoteException
import dev.brunofelix.movies.core.domain.model.TvShow
import javax.inject.Inject

interface TvShowRemoteDataSource {
    fun getPopularPagingSource(): PagingSource<Int, TvShow>
    fun getTopRatedPagingSource(): PagingSource<Int, TvShow>
    suspend fun getDetails(id: Long): Result<TvShow>
}

class TvShowRemoteDataSourceImpl @Inject constructor(
    private val service: TvShowService
) : TvShowRemoteDataSource {

    override fun getPopularPagingSource(): PagingSource<Int, TvShow> {
        return BasePagingSource { page ->
            runCatching {
                service.getPopulars(page).mapOrThrow { it.toDomainList() }
            }.recoverCatching { throw it.toRemoteException() }
        }
    }

    override fun getTopRatedPagingSource(): PagingSource<Int, TvShow> {
        return BasePagingSource { page ->
            runCatching {
                service.getTopRated(page).mapOrThrow { it.toDomainList() }
            }.recoverCatching { throw it.toRemoteException() }
        }
    }

    override suspend fun getDetails(id: Long): Result<TvShow> {
        return runCatching {
            service.getDetails(id).mapOrThrow { it.toDomain() }
        }.recoverCatching { throw it.toRemoteException() }
    }
}
