package dev.brunofelix.movies.domain.use_case

import app.cash.turbine.test
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.repository.MediaRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class GetFavoriteMediasUseCaseImplTest : DescribeSpec({

    val repository = mockk<MediaRepository>()
    val useCase = GetFavoriteMediasUseCaseImpl(repository)

    describe("invoke") {
        it("should emit the favorite medias from the repository") {
            runTest {
                val favorites = listOf(Media(id = 1L), Media(id = 2L))
                every { repository.getFavoriteMedias() } returns flowOf(favorites)

                useCase().test {
                    awaitItem() shouldBe favorites
                    awaitComplete()
                }
            }
        }
    }
})
