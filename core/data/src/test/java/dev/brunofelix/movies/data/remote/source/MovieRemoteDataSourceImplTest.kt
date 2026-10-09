package dev.brunofelix.movies.data.remote.source

import dev.brunofelix.movies.data.remote.MovieApi
import dev.brunofelix.movies.data.remote.dto.CreditsRootDto
import dev.brunofelix.movies.data.remote.dto.VideoRootDto
import dev.brunofelix.movies.data.remote.dto.movie.MovieRootDto
import dev.brunofelix.movies.data.test_util.DtoFactory
import dev.brunofelix.movies.domain.model.enums.ReleaseType
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

class MovieRemoteDataSourceImplTest : DescribeSpec({

    val api = mockk<MovieApi>()
    val dataSource = MovieRemoteDataSourceImpl(api)
    val page = MovieRootDto(results = listOf(DtoFactory.movieDto(1L), DtoFactory.movieDto(2L)))

    beforeTest { clearAllMocks() }

    describe("list endpoints") {
        it("should map the popular movies page to domain models") {
            runTest {
                coEvery { api.getPopulars(1) } returns Response.success(page)

                dataSource.getPopulars(1).getOrThrow().map { it.id } shouldBe listOf(1L, 2L)
            }
        }

        it("should map the upcoming movies page to domain models") {
            runTest {
                coEvery { api.getUpcoming(2) } returns Response.success(page)

                dataSource.getUpcoming(2).getOrThrow().map { it.title } shouldBe listOf("Movie 1", "Movie 2")
            }
        }

        it("should map the top rated movies page to domain models") {
            runTest {
                coEvery { api.getTopRated(3) } returns Response.success(page)

                dataSource.getTopRated(3).getOrThrow().size shouldBe 2
            }
        }

        it("should map the search results to domain models") {
            runTest {
                coEvery { api.search("dune", 1) } returns Response.success(page)

                dataSource.search("dune", 1).getOrThrow().size shouldBe 2
            }
        }

        it("should fail with a RemoteException when the API returns an error") {
            runTest {
                coEvery { api.getPopulars(1) } returns Response.error(500, "".toResponseBody())

                dataSource.getPopulars(1).exceptionOrNull().shouldBeInstanceOf<RemoteException.ServerError>()
            }
        }
    }

    describe("getDetails") {
        it("should map the movie details") {
            runTest {
                coEvery { api.getDetails(7L) } returns Response.success(DtoFactory.movieDto(7L))

                dataSource.getDetails(7L).getOrThrow().id shouldBe 7L
            }
        }
    }

    describe("getVideos") {
        it("should map the videos") {
            runTest {
                coEvery { api.getVideos(7L) } returns Response.success(
                    VideoRootDto(results = listOf(DtoFactory.videoDto("k1")))
                )

                dataSource.getVideos(7L).getOrThrow().single().key shouldBe "k1"
            }
        }
    }

    describe("getCast") {
        it("should map the credits sorted by billing order") {
            runTest {
                coEvery { api.getCredits(7L) } returns Response.success(
                    CreditsRootDto(cast = listOf(DtoFactory.castDto(1L, order = 2), DtoFactory.castDto(2L, order = 1)))
                )

                dataSource.getCast(7L).getOrThrow().map { it.id } shouldBe listOf(2L, 1L)
            }
        }
    }

    describe("getReleases") {
        it("should ask for theatrical release types sorted by popularity") {
            runTest {
                coEvery { api.discover(any(), any(), any(), any(), any()) } returns Response.success(page)

                dataSource.getReleases("2024-01-01", "2024-01-31", ReleaseType.THEATERS, 1).getOrThrow().size shouldBe 2

                coVerify {
                    api.discover(
                        startDate = "2024-01-01",
                        endDate = "2024-01-31",
                        releaseType = "2|3",
                        sortBy = "popularity.desc",
                        page = 1
                    )
                }
            }
        }

        it("should ask for the digital release type for streaming") {
            runTest {
                coEvery { api.discover(any(), any(), any(), any(), any()) } returns Response.success(page)

                dataSource.getReleases("2024-01-01", "2024-01-31", ReleaseType.STREAMING, 2)

                coVerify { api.discover("2024-01-01", "2024-01-31", "4", "popularity.desc", 2) }
            }
        }
    }
})
