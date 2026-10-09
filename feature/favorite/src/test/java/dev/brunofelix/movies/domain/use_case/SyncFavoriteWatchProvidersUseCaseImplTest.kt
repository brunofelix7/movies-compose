package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.LocalException
import dev.brunofelix.movies.domain.util.exception.RemoteException
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class SyncFavoriteWatchProvidersUseCaseImplTest : DescribeSpec({

    val mediaRepository = mockk<MediaRepository>()
    val movieRepository = mockk<MovieRepository>()
    val tvShowRepository = mockk<TvShowRepository>()
    val useCase = SyncFavoriteWatchProvidersUseCaseImpl(mediaRepository, movieRepository, tvShowRepository)

    val netflix = listOf(WatchProvider(id = 8L, name = "Netflix"))
    val max = listOf(WatchProvider(id = 1899L, name = "Max"))
    val pendingMovie = Media(id = 1L, type = MediaType.MOVIE)
    val pendingTvShow = Media(id = 2L, type = MediaType.TV_SHOW)
    val syncedMovie = Media(id = 3L, type = MediaType.MOVIE, watchProviders = emptyList())

    beforeTest {
        clearAllMocks()
        coEvery { mediaRepository.updateWatchProviders(any(), any()) } returns Resource.Success(Unit)
    }

    describe("invoke") {
        it("should store the streaming services of every favorite that has none yet") {
            runTest {
                every { mediaRepository.getFavoriteMedias() } returns flowOf(listOf(pendingMovie, pendingTvShow))
                coEvery { movieRepository.getWatchProviders(1L) } returns Resource.Success(netflix)
                coEvery { tvShowRepository.getWatchProviders(2L) } returns Resource.Success(max)

                useCase()

                coVerify(exactly = 1) { mediaRepository.updateWatchProviders(1L, netflix) }
                coVerify(exactly = 1) { mediaRepository.updateWatchProviders(2L, max) }
            }
        }

        it("should skip the favorites whose streaming services were already fetched") {
            runTest {
                every { mediaRepository.getFavoriteMedias() } returns flowOf(listOf(syncedMovie))

                useCase()

                coVerify(exactly = 0) { movieRepository.getWatchProviders(any()) }
                coVerify(exactly = 0) { mediaRepository.updateWatchProviders(any(), any()) }
            }
        }

        it("should leave a favorite pending when its request fails") {
            runTest {
                every { mediaRepository.getFavoriteMedias() } returns flowOf(listOf(pendingMovie, pendingTvShow))
                coEvery { movieRepository.getWatchProviders(1L) } returns Resource.Error(RemoteException.NoInternet())
                coEvery { tvShowRepository.getWatchProviders(2L) } returns Resource.Success(max)

                useCase()

                coVerify(exactly = 0) { mediaRepository.updateWatchProviders(1L, any()) }
                coVerify(exactly = 1) { mediaRepository.updateWatchProviders(2L, max) }
            }
        }

        it("should do nothing when the favorites cannot be read") {
            runTest {
                every { mediaRepository.getFavoriteMedias() } returns flow { throw LocalException.DatabaseError() }

                useCase()

                coVerify(exactly = 0) { mediaRepository.updateWatchProviders(any(), any()) }
            }
        }
    }
})
