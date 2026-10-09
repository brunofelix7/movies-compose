package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class GetMovieVideosUseCaseImplTest : DescribeSpec({

    val repository = mockk<MovieRepository>()
    val useCase = GetMovieVideosUseCaseImpl(repository)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(listOf(Video(key = "k")))
                coEvery { repository.getVideos(7L) } returns expected

                useCase(7L) shouldBe expected
                coVerify(exactly = 1) { repository.getVideos(7L) }
            }
        }
    }
})
