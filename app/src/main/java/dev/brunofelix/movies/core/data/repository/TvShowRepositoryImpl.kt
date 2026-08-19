package dev.brunofelix.movies.core.data.repository

import dev.brunofelix.movies.core.data.remote.source.TvShowRemoteDataSource
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import dev.brunofelix.movies.core.domain.util.toResource
import javax.inject.Inject

/**
 * Implementation of [TvShowRepository].
 *
 * @property remoteDataSource The source for remote TV show data.
 */
class TvShowRepositoryImpl @Inject constructor(
    private val remoteDataSource: TvShowRemoteDataSource
) : TvShowRepository {

    override suspend fun getPopularTvShows(
        page: Int
    ) = remoteDataSource.getPopulars(page).toResource()

    override suspend fun getTopRatedTvShows(
        page: Int
    ) = remoteDataSource.getTopRated(page).toResource()

    override suspend fun getDetails(
        id: Long
    ) = remoteDataSource.getDetails(id).toResource()
}
