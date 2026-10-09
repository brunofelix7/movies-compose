package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class GetPopularMoviesUseCaseImplTest : DescribeSpec({

    val repository = mockk<MovieRepository>()
    val useCase = GetPopularMoviesUseCaseImpl(repository)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(listOf(Movie(id = 1L)))
                coEvery { repository.getPopularMovies(1) } returns expected

                useCase(1) shouldBe expected
                coVerify(exactly = 1) { repository.getPopularMovies(1) }
            }
        }
    }
})
