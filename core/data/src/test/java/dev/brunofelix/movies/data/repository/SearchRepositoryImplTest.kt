package dev.brunofelix.movies.data.repository

import dev.brunofelix.movies.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.data.remote.source.TvShowRemoteDataSource
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.RemoteException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class SearchRepositoryImplTest : DescribeSpec({

    val movieDataSource = mockk<MovieRemoteDataSource>()
    val tvShowDataSource = mockk<TvShowRemoteDataSource>()
    val repository = SearchRepositoryImpl(movieDataSource, tvShowDataSource)

    beforeTest { clearAllMocks() }

    describe("search") {
        it("should return the movies followed by the TV shows of the same page") {
            runTest {
                coEvery { movieDataSource.search("dark", 1) } returns Result.success(listOf(Movie(id = 1L)))
                coEvery { tvShowDataSource.search("dark", 1) } returns Result.success(listOf(TvShow(id = 2L)))

                val result = repository.search("dark", 1)

                result.shouldBeInstanceOf<Resource.Success<*>>()
                (result as Resource.Success).data.map { it.id to it.type } shouldBe listOf(
                    1L to MediaType.MOVIE,
                    2L to MediaType.TV_SHOW
                )
            }
        }

        it("should return Error when the movie search fails") {
            runTest {
                val error = RemoteException.NoInternet()
                coEvery { movieDataSource.search("dark", 1) } returns Result.failure(error)

                repository.search("dark", 1) shouldBe Resource.Error(error)
            }
        }

        it("should return Error when the TV show search fails") {
            runTest {
                val error = RemoteException.ServerError()
                coEvery { movieDataSource.search("dark", 1) } returns Result.success(emptyList())
                coEvery { tvShowDataSource.search("dark", 1) } returns Result.failure(error)

                repository.search("dark", 1) shouldBe Resource.Error(error)
            }
        }
    }
})
