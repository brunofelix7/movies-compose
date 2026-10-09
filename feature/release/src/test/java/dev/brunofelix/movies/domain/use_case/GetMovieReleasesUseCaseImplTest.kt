package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.enums.ReleaseType
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class GetMovieReleasesUseCaseImplTest : DescribeSpec({

    val repository = mockk<MovieRepository>()
    val useCase = GetMovieReleasesUseCaseImpl(repository)
    val month = ReleaseMonth(2024, 1)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(listOf(Movie(id = 1L)))
                coEvery { repository.getReleases(month, ReleaseType.THEATERS, 1) } returns expected

                useCase(month, ReleaseType.THEATERS, 1) shouldBe expected
                coVerify(exactly = 1) { repository.getReleases(month, ReleaseType.THEATERS, 1) }
            }
        }
    }
})
