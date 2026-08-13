package dev.brunofelix.movies.test_util.fake

import androidx.paging.PagingSource
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.data.util.exception.RemoteException
import dev.brunofelix.movies.test_util.factory.MovieDtoFactory

class FakeMovieRemoteDataSource : MovieRemoteDataSource {

    private var shouldReturnError = false

    private val fakeDataSource = listOf(
        MovieDtoFactory().create(FakeMovie.JohnWick),
        MovieDtoFactory().create(FakeMovie.Avengers),
        MovieDtoFactory().create(FakeMovie.AlienRomulus)
    )

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override fun getPopularPagingSource(): PagingSource<Int, Movie> {
        TODO("Not yet implemented")
    }

    override fun getUpcomingPagingSource(): PagingSource<Int, Movie> {
        TODO("Not yet implemented")
    }

    override fun getTopRatedPagingSource(): PagingSource<Int, Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getDetails(id: Long): Result<Movie> {
        if (shouldReturnError) {
            return Result.failure(RemoteException(0, null))
        }
        val movieDto = fakeDataSource.find { it.id == id }
        return if (movieDto != null) {
            Result.success(movieDto.toDomain())
        } else {
            Result.failure(NoSuchElementException("Movie not found"))
        }
    }
}