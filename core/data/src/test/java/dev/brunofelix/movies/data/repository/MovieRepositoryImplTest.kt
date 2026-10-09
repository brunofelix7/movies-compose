package dev.brunofelix.movies.data.repository

import dev.brunofelix.movies.data.local.source.RegionLocalDataSource
import dev.brunofelix.movies.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.model.enums.ReleaseType
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.RemoteException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class MovieRepositoryImplTest : DescribeSpec({

    val remoteDataSource = mockk<MovieRemoteDataSource>()
    val regionDataSource = mockk<RegionLocalDataSource>()
    val repository = MovieRepositoryImpl(remoteDataSource, regionDataSource)
    val movies = listOf(Movie(id = 1L), Movie(id = 2L))
    val error = RemoteException.NoInternet()

    beforeTest {
        clearAllMocks()
        every { regionDataSource.getRegion() } returns "BR"
    }

    describe("list operations") {
        it("should return the popular movies") {
            runTest {
                coEvery { remoteDataSource.getPopulars(1) } returns Result.success(movies)

                repository.getPopularMovies(1) shouldBe Resource.Success(movies)
            }
        }

        it("should return the upcoming movies") {
            runTest {
                coEvery { remoteDataSource.getUpcoming(2) } returns Result.success(movies)

                repository.getUpcomingMovies(2) shouldBe Resource.Success(movies)
            }
        }

        it("should return the top rated movies") {
            runTest {
                coEvery { remoteDataSource.getTopRated(3) } returns Result.success(movies)

                repository.getTopRatedMovies(3) shouldBe Resource.Success(movies)
            }
        }

        it("should convert a failure into an Error") {
            runTest {
                coEvery { remoteDataSource.getPopulars(1) } returns Result.failure(error)

                repository.getPopularMovies(1) shouldBe Resource.Error(error)
            }
        }
    }

    describe("detail operations") {
        it("should return the movie details") {
            runTest {
                coEvery { remoteDataSource.getDetails(1L) } returns Result.success(movies.first())

                repository.getDetails(1L) shouldBe Resource.Success(movies.first())
            }
        }

        it("should return the movie videos") {
            runTest {
                val videos = listOf(Video(key = "k"))
                coEvery { remoteDataSource.getVideos(1L) } returns Result.success(videos)

                repository.getVideos(1L) shouldBe Resource.Success(videos)
            }
        }

        it("should return the movie cast") {
            runTest {
                val cast = listOf(Cast(id = 9L))
                coEvery { remoteDataSource.getCast(1L) } returns Result.success(cast)

                repository.getCast(1L) shouldBe Resource.Success(cast)
            }
        }

        it("should return the watch providers of the user's region") {
            runTest {
                val providers = listOf(WatchProvider(id = 8L))
                coEvery { remoteDataSource.getWatchProviders(1L, "BR") } returns Result.success(providers)

                repository.getWatchProviders(1L) shouldBe Resource.Success(providers)
            }
        }

        it("should convert a watch providers failure into an Error") {
            runTest {
                coEvery { remoteDataSource.getWatchProviders(1L, "BR") } returns Result.failure(error)

                repository.getWatchProviders(1L) shouldBe Resource.Error(error)
            }
        }
    }

    describe("getReleases") {
        it("should request the releases between the month bounds") {
            runTest {
                coEvery {
                    remoteDataSource.getReleases("2024-02-01", "2024-02-29", ReleaseType.STREAMING, 1)
                } returns Result.success(movies)

                repository.getReleases(ReleaseMonth(2024, 2), ReleaseType.STREAMING, 1) shouldBe Resource.Success(movies)
            }
        }
    }
})
