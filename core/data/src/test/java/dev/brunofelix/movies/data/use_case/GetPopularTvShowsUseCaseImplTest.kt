package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class GetPopularTvShowsUseCaseImplTest : DescribeSpec({

    val repository = mockk<TvShowRepository>()
    val useCase = GetPopularTvShowsUseCaseImpl(repository)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(listOf(TvShow(id = 1L)))
                coEvery { repository.getPopularTvShows(1) } returns expected

                useCase(1) shouldBe expected
                coVerify(exactly = 1) { repository.getPopularTvShows(1) }
            }
        }
    }
})
