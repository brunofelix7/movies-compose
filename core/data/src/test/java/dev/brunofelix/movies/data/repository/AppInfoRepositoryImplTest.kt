package dev.brunofelix.movies.data.repository

import dev.brunofelix.movies.data.local.source.AppInfoLocalDataSource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class AppInfoRepositoryImplTest : DescribeSpec({

    describe("getAppVersion") {
        it("should return the version name from the local data source") {
            val localDataSource = mockk<AppInfoLocalDataSource> {
                every { getVersionName() } returns "1.0.1"
            }

            AppInfoRepositoryImpl(localDataSource).getAppVersion() shouldBe "1.0.1"
        }
    }
})
