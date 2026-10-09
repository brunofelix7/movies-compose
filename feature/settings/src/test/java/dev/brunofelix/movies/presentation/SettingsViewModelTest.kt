package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.use_case.GetAppVersionUseCase
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.domain.use_case.SaveLanguageUseCase
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.LocalException
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.feature.settings.R
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest : DescribeSpec({

    val testDispatcher = UnconfinedTestDispatcher()
    val getLanguageUseCase = mockk<GetLanguageUseCase>()
    val getAppVersionUseCase = mockk<GetAppVersionUseCase>()
    val saveLanguageUseCase = mockk<SaveLanguageUseCase>()
    var language = MutableStateFlow(LanguageEnum.ENGLISH)

    fun viewModel() = SettingsViewModel(getLanguageUseCase, getAppVersionUseCase, saveLanguageUseCase)

    beforeSpec { Dispatchers.setMain(testDispatcher) }
    afterSpec { Dispatchers.resetMain() }

    beforeTest {
        clearAllMocks()
        language = MutableStateFlow(LanguageEnum.PORTUGUESE)
        every { getLanguageUseCase() } returns language
        every { getAppVersionUseCase() } returns "1.0.1"
    }

    describe("uiState") {
        it("should show the saved language, every option and the app version") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }

                viewModel.uiState.value shouldBe SettingsUiState(
                    languages = LanguageEnum.entries,
                    selectedLanguage = LanguageEnum.PORTUGUESE,
                    appVersion = "1.0.1"
                )
            }
        }

        it("should follow the saved language") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }

                language.value = LanguageEnum.SPANISH

                viewModel.uiState.value.selectedLanguage shouldBe LanguageEnum.SPANISH
            }
        }
    }

    describe("onAction") {
        it("should save the selected language") {
            runTest(testDispatcher) {
                coEvery { saveLanguageUseCase(LanguageEnum.ENGLISH) } returns Resource.Success(Unit)

                viewModel().onAction(SettingsUiAction.OnLanguageSelected(LanguageEnum.ENGLISH))

                coVerify { saveLanguageUseCase(LanguageEnum.ENGLISH) }
            }
        }

        it("should show a toast when the language cannot be saved") {
            runTest(testDispatcher) {
                coEvery { saveLanguageUseCase(any()) } returns Resource.Error(LocalException.DatabaseError())
                val viewModel = viewModel()
                val events = mutableListOf<SettingsUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }

                viewModel.onAction(SettingsUiAction.OnLanguageSelected(LanguageEnum.ENGLISH))

                events shouldBe listOf(
                    SettingsUiEvent.ShowToast(UiText.StringResource(R.string.settings_language_error))
                )
                eventJob.cancel()
            }
        }

        it("should emit NavigateBack on OnBack") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                val events = mutableListOf<SettingsUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }

                viewModel.onAction(SettingsUiAction.OnBack)

                events shouldBe listOf(SettingsUiEvent.NavigateBack)
                eventJob.cancel()
            }
        }
    }
})
