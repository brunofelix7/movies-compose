package dev.brunofelix.movies.test_util.fake

import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.model.Video
import dev.brunofelix.movies.core.domain.util.exception.RemoteException
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

    override suspend fun getPopulars(page: Int): Result<List<Movie>> = allMovies()

    override suspend fun getUpcoming(page: Int): Result<List<Movie>> = allMovies()

    override suspend fun getTopRated(page: Int): Result<List<Movie>> = allMovies()

    override suspend fun search(query: String, page: Int): Result<List<Movie>> {
        if (shouldReturnError) {
            return Result.failure(RemoteException.Unknown())
        }
        return Result.success(fakeDataSource.map { it.toDomain() }.filter {
            it.title.contains(query, ignoreCase = true)
        })
    }

    override suspend fun getDetails(id: Long): Result<Movie> {
        if (shouldReturnError) {
            return Result.failure(RemoteException.Unknown())
        }
        val movieDto = fakeDataSource.find { it.id == id }
        return if (movieDto != null) {
            Result.success(movieDto.toDomain())
        } else {
            Result.failure(NoSuchElementException("Movie not found"))
        }
    }

    override suspend fun getVideos(id: Long): Result<List<Video>> {
        if (shouldReturnError) {
            return Result.failure(RemoteException.Unknown())
        }
        return Result.success(
            listOf(Video(key = "abc", site = "YouTube", type = "Trailer"))
        )
    }

    private fun allMovies(): Result<List<Movie>> {
        if (shouldReturnError) {
            return Result.failure(RemoteException.Unknown())
        }
        return Result.success(fakeDataSource.map { it.toDomain() })
    }
}
