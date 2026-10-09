package dev.brunofelix.movies.domain.use_case

import app.cash.turbine.test
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.repository.LanguageRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class GetLanguageUseCaseImplTest : DescribeSpec({

    val repository = mockk<LanguageRepository>()
    val useCase = GetLanguageUseCaseImpl(repository)

    describe("invoke") {
        it("should emit the language from the repository") {
            runTest {
                every { repository.getLanguage() } returns flowOf(LanguageEnum.PORTUGUESE, LanguageEnum.ENGLISH)

                useCase().test {
                    awaitItem() shouldBe LanguageEnum.PORTUGUESE
                    awaitItem() shouldBe LanguageEnum.ENGLISH
                    awaitComplete()
                }
            }
        }
    }
})
