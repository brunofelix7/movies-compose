package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class GetMovieDetailUseCaseImplTest : DescribeSpec({

    val repository = mockk<MovieRepository>()
    val useCase = GetMovieDetailUseCaseImpl(repository)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(Movie(id = 7L))
                coEvery { repository.getDetails(7L) } returns expected

                useCase(7L) shouldBe expected
                coVerify(exactly = 1) { repository.getDetails(7L) }
            }
        }
    }
})
