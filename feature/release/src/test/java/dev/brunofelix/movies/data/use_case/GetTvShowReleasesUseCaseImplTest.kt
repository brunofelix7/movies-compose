package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class GetTvShowReleasesUseCaseImplTest : DescribeSpec({

    val repository = mockk<TvShowRepository>()
    val useCase = GetTvShowReleasesUseCaseImpl(repository)
    val month = ReleaseMonth(2024, 1)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(listOf(TvShow(id = 1L)))
                coEvery { repository.getReleases(month, 1) } returns expected

                useCase(month, 1) shouldBe expected
                coVerify(exactly = 1) { repository.getReleases(month, 1) }
            }
        }
    }
})
