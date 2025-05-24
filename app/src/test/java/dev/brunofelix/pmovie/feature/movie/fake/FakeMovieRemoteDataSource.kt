package dev.brunofelix.pmovie.feature.movie.fake

import dev.brunofelix.pmovie.core.data.remote.dto.MovieDto
import dev.brunofelix.pmovie.core.data.remote.dto.ResultDto
import dev.brunofelix.pmovie.core.data.remote.paging.MoviePopularPagingSource
import dev.brunofelix.pmovie.core.data.remote.paging.MovieUpcomingPagingSource
import dev.brunofelix.pmovie.feature.movie.factory.MovieDtoFactory
import dev.brunofelix.pmovie.core.util.exception.RemoteException
import dev.brunofelix.pmovie.feature.movie.domain.data_source.MovieRemoteDataSource
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

    override suspend fun getPopular(page: Int): Response<MovieDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcoming(page: Int): Response<MovieDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getDetails(id: Long): Response<ResultDto> {
        if (shouldReturnError) {
            throw RemoteException()
        }
        return Response.success(fakeDataSource.find { it.id == id })
    }
}