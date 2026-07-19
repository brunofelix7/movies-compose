package dev.brunofelix.movies.test_util.fake

import dev.brunofelix.movies.core.data.api.mapper.toDomain
import dev.brunofelix.movies.core.data.api.paging.MoviePopularPagingSource
import dev.brunofelix.movies.core.data.api.paging.MovieUpcomingPagingSource
import dev.brunofelix.movies.core.data.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.util.exception.RemoteException
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

    override fun getPopularPagingSource(): MoviePopularPagingSource {
        TODO("Not yet implemented")
    }

    override fun getUpcomingPagingSource(): MovieUpcomingPagingSource {
        TODO("Not yet implemented")
    }

    override suspend fun getPopular(page: Int): Result<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcoming(page: Int): Result<List<Movie>> {
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