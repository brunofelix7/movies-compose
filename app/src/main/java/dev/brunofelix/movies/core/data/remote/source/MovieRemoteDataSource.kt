package dev.brunofelix.movies.core.data.remote.source

import androidx.paging.PagingSource
import dev.brunofelix.movies.core.data.remote.MovieService
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.paging.BasePagingSource
import dev.brunofelix.movies.core.data.util.extension.mapOrThrow
import dev.brunofelix.movies.core.domain.model.Movie
import javax.inject.Inject

interface MovieRemoteDataSource {
    fun getPopularPagingSource(): PagingSource<Int, Movie>
    fun getUpcomingPagingSource(): PagingSource<Int, Movie>
    fun getTopRatedPagingSource(): PagingSource<Int, Movie>
    suspend fun getDetails(id: Long): Result<Movie>
}

class MovieRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : MovieRemoteDataSource {

    override fun getPopularPagingSource(): PagingSource<Int, Movie> {
        return BasePagingSource { page ->
            runCatching {
                service.getPopulars(page).mapOrThrow {
                    it.results?.map { result -> result.toDomain() } ?: emptyList()
                }
            }
        }
    }

    override fun getUpcomingPagingSource(): PagingSource<Int, Movie> {
        return BasePagingSource { page ->
            runCatching {
                service.getUpcoming(page).mapOrThrow {
                    it.results?.map { result -> result.toDomain() } ?: emptyList()
                }
            }
        }
    }

    override fun getTopRatedPagingSource(): PagingSource<Int, Movie> {
        return BasePagingSource { page ->
            runCatching {
                service.getTopRated(page).mapOrThrow {
                    it.results?.map { result -> result.toDomain() } ?: emptyList()
                }
            }
        }
    }

    override suspend fun getDetails(id: Long): Result<Movie> {
        return runCatching {
            service.getDetails(id).mapOrThrow { it.toDomain() }
        }
    }
}
