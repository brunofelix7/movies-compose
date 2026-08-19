package dev.brunofelix.movies.core.data.repository

import dev.brunofelix.movies.core.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.util.toResource
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
}
