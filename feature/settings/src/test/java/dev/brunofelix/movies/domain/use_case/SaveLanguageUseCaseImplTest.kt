package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.repository.LanguageRepository
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class SaveLanguageUseCaseImplTest : DescribeSpec({

    val repository = mockk<LanguageRepository>()
    val useCase = SaveLanguageUseCaseImpl(repository)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            runTest {
                val expected = Resource.Success(Unit)
                coEvery { repository.saveLanguage(LanguageEnum.SPANISH) } returns expected

                useCase(LanguageEnum.SPANISH) shouldBe expected
                coVerify(exactly = 1) { repository.saveLanguage(LanguageEnum.SPANISH) }
            }
        }
    }
})
