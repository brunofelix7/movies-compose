package dev.brunofelix.movies.core.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.data.source.MovieLocalDataSource
import dev.brunofelix.movies.core.data.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun save(movie: Movie) {
        localDataSource.insert(movie.toMovieEntity())
    }

    override suspend fun delete(movie: Movie) {
        localDataSource.delete(movie.toMovieEntity())
    }

    override suspend fun isFavorite(id: Long): Boolean {
        return localDataSource.getById(id)?.toMovie() != null
    }

    override suspend fun getDetails(id: Long): Movie? {
        return remoteDataSource.getDetails(id).body()?.toMovie()
    }

    override fun fetchFavorites(): Flow<List<Movie>?> {
        return localDataSource.getAll().map { entityList ->
            entityList?.map { it.toMovie() }
        }
    }

    override fun fetchPopulars(pagingConfig: PagingConfig): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                remoteDataSource.getPopularPagingSource()
            }
        ).flow
    }

    override fun fetchUpcoming(pagingConfig: PagingConfig): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                remoteDataSource.getUpcomingPagingSource()
            }
        ).flow
    }
}