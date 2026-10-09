package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class UpdateFavoriteWatchProvidersUseCaseImplTest : DescribeSpec({

    val repository = mockk<MediaRepository>()
    val useCase = UpdateFavoriteWatchProvidersUseCaseImpl(repository)
    val providers = listOf(WatchProvider(id = 8L, name = "Netflix"))

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(Unit)
                coEvery { repository.updateWatchProviders(1L, providers) } returns expected

                useCase(1L, providers) shouldBe expected
                coVerify(exactly = 1) { repository.updateWatchProviders(1L, providers) }
            }
        }
    }
})
