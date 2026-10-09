package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class GetTvShowCastUseCaseImplTest : DescribeSpec({

    val repository = mockk<TvShowRepository>()
    val useCase = GetTvShowCastUseCaseImpl(repository)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(listOf(Cast(id = 1L)))
                coEvery { repository.getCast(3L) } returns expected

                useCase(3L) shouldBe expected
                coVerify(exactly = 1) { repository.getCast(3L) }
            }
        }
    }
})
