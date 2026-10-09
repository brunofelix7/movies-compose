package dev.brunofelix.movies.data.repository

import dev.brunofelix.movies.data.remote.source.TvShowRemoteDataSource
import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.model.Episode
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.toResource
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

    override suspend fun getVideos(
        id: Long
    ): Resource<List<Video>> = remoteDataSource.getVideos(id).toResource()

    override suspend fun getCast(
        id: Long
    ): Resource<List<Cast>> = remoteDataSource.getCast(id).toResource()

    override suspend fun getSeasonEpisodes(
        id: Long,
        seasonNumber: Int
    ): Resource<List<Episode>> = remoteDataSource.getSeasonEpisodes(id, seasonNumber).toResource()

    override suspend fun getReleases(
        month: ReleaseMonth,
        page: Int
    ) = remoteDataSource.getReleases(
        startDate = month.startDate,
        endDate = month.endDate,
        page = page
    ).toResource()
}
