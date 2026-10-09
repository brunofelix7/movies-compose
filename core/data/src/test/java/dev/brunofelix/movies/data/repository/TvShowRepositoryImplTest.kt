package dev.brunofelix.movies.data.repository

import dev.brunofelix.movies.data.remote.source.TvShowRemoteDataSource
import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.model.Episode
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.RemoteException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class TvShowRepositoryImplTest : DescribeSpec({

    val remoteDataSource = mockk<TvShowRemoteDataSource>()
    val repository = TvShowRepositoryImpl(remoteDataSource)
    val tvShows = listOf(TvShow(id = 1L))

    beforeTest { clearAllMocks() }

    describe("list operations") {
        it("should return the popular TV shows") {
            runTest {
                coEvery { remoteDataSource.getPopulars(1) } returns Result.success(tvShows)

                repository.getPopularTvShows(1) shouldBe Resource.Success(tvShows)
            }
        }

        it("should return the top rated TV shows") {
            runTest {
                coEvery { remoteDataSource.getTopRated(1) } returns Result.success(tvShows)

                repository.getTopRatedTvShows(1) shouldBe Resource.Success(tvShows)
            }
        }

        it("should convert a failure into an Error") {
            runTest {
                val error = RemoteException.ServerError()
                coEvery { remoteDataSource.getTopRated(1) } returns Result.failure(error)

                repository.getTopRatedTvShows(1) shouldBe Resource.Error(error)
            }
        }
    }

    describe("detail operations") {
        it("should return the TV show details") {
            runTest {
                coEvery { remoteDataSource.getDetails(1L) } returns Result.success(tvShows.first())

                repository.getDetails(1L) shouldBe Resource.Success(tvShows.first())
            }
        }

        it("should return the TV show videos") {
            runTest {
                val videos = listOf(Video(key = "k"))
                coEvery { remoteDataSource.getVideos(1L) } returns Result.success(videos)

                repository.getVideos(1L) shouldBe Resource.Success(videos)
            }
        }

        it("should return the TV show cast") {
            runTest {
                val cast = listOf(Cast(id = 2L))
                coEvery { remoteDataSource.getCast(1L) } returns Result.success(cast)

                repository.getCast(1L) shouldBe Resource.Success(cast)
            }
        }

        it("should return the episodes of a season") {
            runTest {
                val episodes = listOf(Episode(id = 3L))
                coEvery { remoteDataSource.getSeasonEpisodes(1L, 2) } returns Result.success(episodes)

                repository.getSeasonEpisodes(1L, 2) shouldBe Resource.Success(episodes)
            }
        }
    }

    describe("getReleases") {
        it("should request the premieres between the month bounds") {
            runTest {
                coEvery { remoteDataSource.getReleases("2024-04-01", "2024-04-30", 2) } returns Result.success(tvShows)

                repository.getReleases(ReleaseMonth(2024, 4), 2) shouldBe Resource.Success(tvShows)
            }
        }
    }
})
