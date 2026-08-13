package dev.brunofelix.movies.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.data.remote.source.TvShowRemoteDataSource
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.domain.util.toResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val remoteDataSource: TvShowRemoteDataSource
) : TvShowRepository {

    override fun getPopularTvShows(pagingConfig: PagingConfig): Flow<PagingData<TvShow>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                remoteDataSource.getPopularPagingSource()
            }
        ).flow
    }

    override fun getTopRatedTvShows(pagingConfig: PagingConfig): Flow<PagingData<TvShow>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                remoteDataSource.getTopRatedPagingSource()
            }
        ).flow
    }

    override suspend fun getDetails(id: Long): Resource<TvShow> {
        return remoteDataSource.getDetails(id).toResource()
    }
}
