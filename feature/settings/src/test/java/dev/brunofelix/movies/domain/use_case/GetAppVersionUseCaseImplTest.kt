package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.repository.AppInfoRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class GetAppVersionUseCaseImplTest : DescribeSpec({

    val repository = mockk<AppInfoRepository>()
    val useCase = GetAppVersionUseCaseImpl(repository)

    describe("invoke") {
        it("should delegate to the repository and return its result") {
            val expected = "1.0.1"
            every { repository.getAppVersion() } returns expected

            useCase() shouldBe expected
            verify(exactly = 1) { repository.getAppVersion() }
        }
    }
})
