package dev.brunofelix.movies.core.data.remote.source

import androidx.paging.PagingSource
import dev.brunofelix.movies.core.data.remote.TvShowService
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.paging.BasePagingSource
import dev.brunofelix.movies.core.data.remote.source.base.RemoteDataSource
import dev.brunofelix.movies.core.domain.model.TvShow
import javax.inject.Inject

class TvShowRemoteDataSourceImpl @Inject constructor(
    api: TvShowService
) : RemoteDataSource<TvShowService>(api), TvShowRemoteDataSource {

    override fun getPopularPagingSource(): PagingSource<Int, TvShow> {
        return BasePagingSource { page -> getPopular(page) }
    }

    override fun getTopRatedPagingSource(): PagingSource<Int, TvShow> {
        return BasePagingSource { page -> getTopRated(page) }
    }

    override suspend fun getPopular(page: Int): Result<List<TvShow>> {
        return safeApiCall(
            call = { getPopulars(page) },
            transform = { it.results?.map { result -> result.toDomain() } ?: emptyList() }
        )
    }

    override suspend fun getTopRated(page: Int): Result<List<TvShow>> {
        return safeApiCall(
            call = { getTopRated(page) },
            transform = { it.results?.map { result -> result.toDomain() } ?: emptyList() }
        )
    }

    override suspend fun getDetails(id: Long): Result<TvShow> {
        return safeApiCall(
            call = { getDetails(id) },
            transform = { it.toDomain() }
        )
    }
}
