package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.repository.SearchRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class SearchMediaUseCaseImplTest : DescribeSpec({

    val repository = mockk<SearchRepository>()
    val useCase = SearchMediaUseCaseImpl(repository)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(listOf(Media(id = 1L)))
                coEvery { repository.search("dune", 1) } returns expected

                useCase("dune", 1) shouldBe expected
                coVerify(exactly = 1) { repository.search("dune", 1) }
            }
        }
    }
})
