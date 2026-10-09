package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class DeleteMediaUseCaseImplTest : DescribeSpec({

    val repository = mockk<MediaRepository>()
    val useCase = DeleteMediaUseCaseImpl(repository)
    val media = Media(id = 1L)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(Unit)
                coEvery { repository.delete(media) } returns expected

                useCase(media) shouldBe expected
                coVerify(exactly = 1) { repository.delete(media) }
            }
        }
    }
})
