package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class GetTvShowWatchProvidersUseCaseImplTest : DescribeSpec({

    val repository = mockk<TvShowRepository>()
    val useCase = GetTvShowWatchProvidersUseCaseImpl(repository)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(listOf(WatchProvider(id = 8L, name = "Netflix")))
                coEvery { repository.getWatchProviders(3L) } returns expected

                useCase(3L) shouldBe expected
                coVerify(exactly = 1) { repository.getWatchProviders(3L) }
            }
        }
    }
})
