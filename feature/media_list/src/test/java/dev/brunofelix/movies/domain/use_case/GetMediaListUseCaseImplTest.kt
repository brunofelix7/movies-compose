package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.domain.model.enums.ReleaseType
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.RemoteException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class GetMediaListUseCaseImplTest : DescribeSpec({

    val movieRepository = mockk<MovieRepository>()
    val tvShowRepository = mockk<TvShowRepository>()
    val useCase = GetMediaListUseCaseImpl(movieRepository, tvShowRepository)
    val movies = Resource.Success(listOf(Movie(id = 1L)))
    val tvShows = Resource.Success(listOf(TvShow(id = 2L)))
    val month = ReleaseMonth(2024, 5)

    fun Resource<List<Media>>.idsAndTypes() =
        (this as Resource.Success).data.map { it.id to it.type }

    beforeTest { clearAllMocks() }

    describe("movie lists") {
        it("should load the popular movies as medias") {
            runTest {
                coEvery { movieRepository.getPopularMovies(1) } returns movies

                useCase(MediaListCategory.MOVIE_POPULAR, null, 1).idsAndTypes() shouldBe listOf(1L to MediaType.MOVIE)
            }
        }

        it("should load the upcoming movies") {
            runTest {
                coEvery { movieRepository.getUpcomingMovies(2) } returns movies

                useCase(MediaListCategory.MOVIE_UPCOMING, null, 2).idsAndTypes() shouldBe listOf(1L to MediaType.MOVIE)
            }
        }

        it("should load the top rated movies") {
            runTest {
                coEvery { movieRepository.getTopRatedMovies(3) } returns movies

                useCase(MediaListCategory.MOVIE_TOP_RATED, null, 3).idsAndTypes() shouldBe listOf(1L to MediaType.MOVIE)
            }
        }
    }

    describe("TV show lists") {
        it("should load the popular TV shows as medias") {
            runTest {
                coEvery { tvShowRepository.getPopularTvShows(1) } returns tvShows

                useCase(MediaListCategory.TV_SHOW_POPULAR, null, 1).idsAndTypes() shouldBe listOf(2L to MediaType.TV_SHOW)
            }
        }

        it("should load the top rated TV shows") {
            runTest {
                coEvery { tvShowRepository.getTopRatedTvShows(1) } returns tvShows

                useCase(MediaListCategory.TV_SHOW_TOP_RATED, null, 1).idsAndTypes() shouldBe listOf(2L to MediaType.TV_SHOW)
            }
        }
    }

    describe("release lists") {
        it("should load the theatrical releases of the given month") {
            runTest {
                coEvery { movieRepository.getReleases(month, ReleaseType.THEATERS, 1) } returns movies

                useCase(MediaListCategory.RELEASE_THEATERS, month, 1).idsAndTypes() shouldBe listOf(1L to MediaType.MOVIE)
            }
        }

        it("should load the streaming releases of the given month") {
            runTest {
                coEvery { movieRepository.getReleases(month, ReleaseType.STREAMING, 1) } returns movies

                useCase(MediaListCategory.RELEASE_STREAMING, month, 1).idsAndTypes() shouldBe listOf(1L to MediaType.MOVIE)
            }
        }

        it("should load the series premieres of the given month") {
            runTest {
                coEvery { tvShowRepository.getReleases(month, 1) } returns tvShows

                useCase(MediaListCategory.RELEASE_SERIES, month, 1).idsAndTypes() shouldBe listOf(2L to MediaType.TV_SHOW)
            }
        }

        it("should fall back to the current month when none is given") {
            runTest {
                coEvery { tvShowRepository.getReleases(any(), any()) } returns tvShows

                useCase(MediaListCategory.RELEASE_SERIES, null, 1)

                coVerify { tvShowRepository.getReleases(ReleaseMonth.current(), 1) }
            }
        }
    }

    describe("errors") {
        it("should keep the repository error") {
            runTest {
                val error = RemoteException.NoInternet()
                coEvery { movieRepository.getPopularMovies(1) } returns Resource.Error(error)

                val result = useCase(MediaListCategory.MOVIE_POPULAR, null, 1)

                result.shouldBeInstanceOf<Resource.Error>()
                result.throwable shouldBe error
            }
        }
    }
})
