package dev.brunofelix.movies.test_util.fake

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.domain.util.toResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository (
    private val remoteDataSource: FakeMovieRemoteDataSource,
    private val localDataSource: FakeMovieLocalDataSource
): MovieRepository {

    override fun getPopularMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>> = flow {
        TODO("Not yet implemented")
    }

    override fun getUpcomingMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>> = flow {
        TODO("Not yet implemented")
    }

    override fun getTopRatedMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>> = flow {
        TODO("Not yet implemented")
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDetails(id: Long): Resource<Movie> {
        return remoteDataSource.getDetails(id).toResource()
    }

    override suspend fun save(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun isFavorite(id: Long): Boolean {
        TODO("Not yet implemented")
    }
}