package dev.brunofelix.movies.core.data.repository

import androidx.paging.PagingConfig
import dev.brunofelix.movies.core.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.data.util.extension.asPagerFlow
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

    override fun getPopularMovies(
        pagingConfig: PagingConfig
    ) = pagingConfig.asPagerFlow { remoteDataSource.getPopularPagingSource() }

    override fun getUpcomingMovies(
        pagingConfig: PagingConfig
    ) = pagingConfig.asPagerFlow { remoteDataSource.getUpcomingPagingSource() }

    override fun getTopRatedMovies(
        pagingConfig: PagingConfig
    ) = pagingConfig.asPagerFlow { remoteDataSource.getTopRatedPagingSource() }
}
