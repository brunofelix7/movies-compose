package dev.brunofelix.movies.data.remote.source

import dev.brunofelix.movies.data.remote.TvShowApi
import dev.brunofelix.movies.data.remote.dto.CreditsRootDto
import dev.brunofelix.movies.data.remote.dto.VideoRootDto
import dev.brunofelix.movies.data.remote.dto.tv_show.TvShowRootDto
import dev.brunofelix.movies.data.test_util.DtoFactory
import dev.brunofelix.movies.domain.util.exception.RemoteException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.net.UnknownHostException

class TvShowRemoteDataSourceImplTest : DescribeSpec({

    val api = mockk<TvShowApi>()
    val dataSource = TvShowRemoteDataSourceImpl(api)
    val page = TvShowRootDto(results = listOf(DtoFactory.tvShowDto(1L), DtoFactory.tvShowDto(2L)))

    beforeTest { clearAllMocks() }

    describe("list endpoints") {
        it("should map the popular TV shows page to domain models") {
            runTest {
                coEvery { api.getPopulars(1) } returns Response.success(page)

                dataSource.getPopulars(1).getOrThrow().map { it.id } shouldBe listOf(1L, 2L)
            }
        }

        it("should map the top rated TV shows page to domain models") {
            runTest {
                coEvery { api.getTopRated(1) } returns Response.success(page)

                dataSource.getTopRated(1).getOrThrow().map { it.name } shouldBe listOf("Show 1", "Show 2")
            }
        }

        it("should map the search results to domain models") {
            runTest {
                coEvery { api.search("dark", 2) } returns Response.success(page)

                dataSource.search("dark", 2).getOrThrow().size shouldBe 2
            }
        }

        it("should fail with a RemoteException when the request throws") {
            runTest {
                coEvery { api.getPopulars(1) } throws UnknownHostException()

                dataSource.getPopulars(1).exceptionOrNull().shouldBeInstanceOf<RemoteException.NoInternet>()
            }
        }

        it("should fail with a RemoteException when the API returns an error") {
            runTest {
                coEvery { api.getTopRated(1) } returns Response.error(401, "".toResponseBody())

                dataSource.getTopRated(1).exceptionOrNull().shouldBeInstanceOf<RemoteException.Unauthorized>()
            }
        }
    }

    describe("getDetails") {
        it("should map the TV show details") {
            runTest {
                coEvery { api.getDetails(3L) } returns Response.success(DtoFactory.tvShowDto(3L))

                dataSource.getDetails(3L).getOrThrow().id shouldBe 3L
            }
        }
    }

    describe("getVideos") {
        it("should map the videos") {
            runTest {
                coEvery { api.getVideos(3L) } returns Response.success(
                    VideoRootDto(results = listOf(DtoFactory.videoDto("k2")))
                )

                dataSource.getVideos(3L).getOrThrow().single().key shouldBe "k2"
            }
        }
    }

    describe("getCast") {
        it("should map the credits") {
            runTest {
                coEvery { api.getCredits(3L) } returns Response.success(
                    CreditsRootDto(cast = listOf(DtoFactory.castDto(9L)))
                )

                dataSource.getCast(3L).getOrThrow().single().id shouldBe 9L
            }
        }
    }

    describe("getSeasonEpisodes") {
        it("should map the episodes of the requested season") {
            runTest {
                coEvery { api.getSeason(3L, 1) } returns Response.success(DtoFactory.seasonDto(1))

                dataSource.getSeasonEpisodes(3L, 1).getOrThrow().map { it.id } shouldBe listOf(101L)
            }
        }
    }

    describe("getReleases") {
        it("should discover by first air date sorted by popularity") {
            runTest {
                coEvery { api.discover(any(), any(), any(), any()) } returns Response.success(page)

                dataSource.getReleases("2024-01-01", "2024-01-31", 1).getOrThrow().size shouldBe 2

                coVerify { api.discover("2024-01-01", "2024-01-31", "popularity.desc", 1) }
            }
        }
    }
})
