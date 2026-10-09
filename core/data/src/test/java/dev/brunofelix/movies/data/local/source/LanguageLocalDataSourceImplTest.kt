package dev.brunofelix.movies.data.local.source

import app.cash.turbine.test
import dev.brunofelix.movies.data.local.preferences.PreferenceStorage
import dev.brunofelix.movies.data.local.preferences.PreferencesKeys
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.util.exception.LocalException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class LanguageLocalDataSourceImplTest : DescribeSpec({

    val preferences = mockk<PreferenceStorage>()
    val dataSource = LanguageLocalDataSourceImpl(preferences)

    beforeTest { clearAllMocks() }

    describe("observe") {
        it("should map the stored code to a language, defaulting to English") {
            runTest {
                every {
                    preferences.observe(PreferencesKeys.LANGUAGE_KEY, LanguageEnum.ENGLISH.code)
                } returns flowOf("pt-BR", "unknown")

                dataSource.observe().test {
                    awaitItem() shouldBe LanguageEnum.PORTUGUESE
                    awaitItem() shouldBe LanguageEnum.ENGLISH
                    awaitComplete()
                }
            }
        }
    }

    describe("save") {
        it("should store the language code") {
            runTest {
                coEvery { preferences.put(PreferencesKeys.LANGUAGE_KEY, "es") } just runs

                dataSource.save(LanguageEnum.SPANISH) shouldBe Result.success(Unit)
                coVerify { preferences.put(PreferencesKeys.LANGUAGE_KEY, "es") }
            }
        }

        it("should fail with a DatabaseError when the storage throws") {
            runTest {
                coEvery { preferences.put(any(), any<String>()) } throws IllegalStateException()

                dataSource.save(LanguageEnum.SPANISH).exceptionOrNull()
                    .shouldBeInstanceOf<LocalException.DatabaseError>()
            }
        }
    }
})
