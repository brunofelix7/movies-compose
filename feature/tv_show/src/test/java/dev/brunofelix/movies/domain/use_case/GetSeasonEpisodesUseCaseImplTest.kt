package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Episode
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class GetSeasonEpisodesUseCaseImplTest : DescribeSpec({

    val repository = mockk<TvShowRepository>()
    val useCase = GetSeasonEpisodesUseCaseImpl(repository)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(listOf(Episode(id = 1L)))
                coEvery { repository.getSeasonEpisodes(3L, 2) } returns expected

                useCase(3L, 2) shouldBe expected
                coVerify(exactly = 1) { repository.getSeasonEpisodes(3L, 2) }
            }
        }
    }
})
