package dev.brunofelix.movies.data.repository

import dev.brunofelix.movies.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.model.enums.ReleaseType
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.toResource
import javax.inject.Inject

/**
 * Implementation of [MovieRepository].
 *
 * @property remoteDataSource The source for remote movie data.
 */
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getDetails(
        id: Long
    ) = remoteDataSource.getDetails(id).toResource()

    override suspend fun getPopularMovies(
        page: Int
    ) = remoteDataSource.getPopulars(page).toResource()

    override suspend fun getUpcomingMovies(
        page: Int
    ) = remoteDataSource.getUpcoming(page).toResource()

    override suspend fun getTopRatedMovies(
        page: Int
    ) = remoteDataSource.getTopRated(page).toResource()

    override suspend fun getVideos(
        id: Long
    ): Resource<List<Video>> = remoteDataSource.getVideos(id).toResource()

    override suspend fun getCast(
        id: Long
    ): Resource<List<Cast>> = remoteDataSource.getCast(id).toResource()

    override suspend fun getReleases(
        month: ReleaseMonth,
        type: ReleaseType,
        page: Int
    ) = remoteDataSource.getReleases(
        startDate = month.startDate,
        endDate = month.endDate,
        type = type,
        page = page
    ).toResource()
}
