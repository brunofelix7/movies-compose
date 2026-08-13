package dev.brunofelix.movies.core.data.remote.source

import androidx.paging.PagingSource
import dev.brunofelix.movies.core.data.remote.TvShowService
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.paging.BasePagingSource
import dev.brunofelix.movies.core.data.util.extension.mapOrThrow
import dev.brunofelix.movies.core.domain.model.TvShow
import javax.inject.Inject

interface TvShowRemoteDataSource {
    fun getPopularPagingSource(): PagingSource<Int, TvShow>
    fun getTopRatedPagingSource(): PagingSource<Int, TvShow>
    suspend fun getPopular(page: Int): Result<List<TvShow>>
    suspend fun getTopRated(page: Int): Result<List<TvShow>>
    suspend fun getDetails(id: Long): Result<TvShow>
}

class TvShowRemoteDataSourceImpl @Inject constructor(
    private val service: TvShowService
) : TvShowRemoteDataSource {

    override fun getPopularPagingSource(): PagingSource<Int, TvShow> {
        return BasePagingSource { page -> getPopular(page) }
    }

    override fun getTopRatedPagingSource(): PagingSource<Int, TvShow> {
        return BasePagingSource { page -> getTopRated(page) }
    }

    override suspend fun getPopular(page: Int): Result<List<TvShow>> {
        return runCatching {
            service.getPopulars(page).mapOrThrow {
                it.results?.map { result -> result.toDomain() } ?: emptyList()
            }
        }
    }

    override suspend fun getTopRated(page: Int): Result<List<TvShow>> {
        return runCatching {
            service.getTopRated(page).mapOrThrow {
                it.results?.map { result -> result.toDomain() } ?: emptyList()
            }
        }
    }

    override suspend fun getDetails(id: Long): Result<TvShow> {
        return runCatching {
            service.getDetails(id).mapOrThrow { it.toDomain() }
        }
    }
}
