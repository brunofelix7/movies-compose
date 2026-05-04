package dev.brunofelix.movies.test_util.fake

import dev.brunofelix.movies.core.data.api.dto.MovieRootDto
import dev.brunofelix.movies.core.data.api.dto.MovieDto
import dev.brunofelix.movies.core.data.api.paging.MoviePopularPagingSource
import dev.brunofelix.movies.core.data.api.paging.MovieUpcomingPagingSource
import dev.brunofelix.movies.core.util.exception.RemoteException
import dev.brunofelix.movies.core.data.source.MovieRemoteDataSource
import dev.brunofelix.movies.test_util.factory.MovieDtoFactory
import retrofit2.Response

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

    override suspend fun getPopular(page: Int): Response<MovieRootDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcoming(page: Int): Response<MovieRootDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getDetails(id: Long): Response<MovieDto> {
        if (shouldReturnError) {
            throw RemoteException(0, null)
        }
        return Response.success(fakeDataSource.find { it.id == id })
    }
}