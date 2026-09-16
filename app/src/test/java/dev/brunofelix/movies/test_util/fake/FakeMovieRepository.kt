package dev.brunofelix.movies.test_util.fake

import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.domain.model.Video
import dev.brunofelix.movies.core.domain.model.enums.ReleaseType
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.domain.util.toResource

class FakeMovieRepository(
    private val remoteDataSource: FakeMovieRemoteDataSource
) : MovieRepository {

    override suspend fun getDetails(id: Long): Resource<Movie> {
        return remoteDataSource.getDetails(id).toResource()
    }

    override suspend fun getPopularMovies(page: Int): Resource<List<Movie>> {
        return remoteDataSource.getPopulars(page).toResource()
    }

    override suspend fun getUpcomingMovies(page: Int): Resource<List<Movie>> {
        return remoteDataSource.getUpcoming(page).toResource()
    }

    override suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>> {
        return remoteDataSource.getTopRated(page).toResource()
    }

    override suspend fun getVideos(id: Long): Resource<List<Video>> {
        return remoteDataSource.getVideos(id).toResource()
    }

    override suspend fun getReleases(
        month: ReleaseMonth,
        type: ReleaseType,
        page: Int
    ): Resource<List<Movie>> {
        return remoteDataSource.getReleases(
            startDate = month.startDate,
            endDate = month.endDate,
            type = type,
            page = page
        ).toResource()
    }
}
