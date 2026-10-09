package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class IsFavoriteMediaUseCaseImplTest : DescribeSpec({

    val repository = mockk<MediaRepository>()
    val useCase = IsFavoriteMediaUseCaseImpl(repository)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(true)
                coEvery { repository.isFavorite(1L) } returns expected

                useCase(1L) shouldBe expected
                coVerify(exactly = 1) { repository.isFavorite(1L) }
            }
        }
    }
})
