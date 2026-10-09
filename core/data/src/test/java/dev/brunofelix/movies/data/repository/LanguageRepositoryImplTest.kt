package dev.brunofelix.movies.data.repository

import app.cash.turbine.test
import dev.brunofelix.movies.data.local.source.LanguageLocalDataSource
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.LocalException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class LanguageRepositoryImplTest : DescribeSpec({

    val localDataSource = mockk<LanguageLocalDataSource>()
    val repository = LanguageRepositoryImpl(localDataSource)

    beforeTest { clearAllMocks() }

    describe("getLanguage") {
        it("should emit the stored language") {
            runTest {
                every { localDataSource.observe() } returns flowOf(LanguageEnum.SPANISH)

                repository.getLanguage().test {
                    awaitItem() shouldBe LanguageEnum.SPANISH
                    awaitComplete()
                }
            }
        }
    }

    describe("saveLanguage") {
        it("should return Success when the language is stored") {
            runTest {
                coEvery { localDataSource.save(LanguageEnum.PORTUGUESE) } returns Result.success(Unit)

                repository.saveLanguage(LanguageEnum.PORTUGUESE) shouldBe Resource.Success(Unit)
            }
        }

        it("should return Error when the language cannot be stored") {
            runTest {
                val error = LocalException.DatabaseError()
                coEvery { localDataSource.save(LanguageEnum.PORTUGUESE) } returns Result.failure(error)

                repository.saveLanguage(LanguageEnum.PORTUGUESE) shouldBe Resource.Error(error)
            }
        }
    }
})
