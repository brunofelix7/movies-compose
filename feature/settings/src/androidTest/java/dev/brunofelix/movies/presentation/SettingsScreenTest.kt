package dev.brunofelix.movies.presentation

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.feature.settings.R
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR

@RunWith(AndroidJUnit4::class)
class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val uiState = SettingsUiState(selectedLanguage = LanguageEnum.PORTUGUESE, appVersion = "1.0.1")

    @Test
    fun shouldRenderTheLanguagesAndTheVersion() {
        composeTestRule.setContent {
            PMovieTheme { SettingsScreen(uiState = uiState, onAction = {}) }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.settings)).assertIsDisplayed()
        LanguageEnum.entries.forEach { composeTestRule.onNodeWithText(it.description).assertIsDisplayed() }
        composeTestRule.onNodeWithText("1.0.1").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.settings_selected_language_icon))
            .assertIsDisplayed()
    }

    @Test
    fun shouldTriggerLanguageSelectionAndBack() {
        val actions = mutableListOf<SettingsUiAction>()
        composeTestRule.setContent {
            PMovieTheme { SettingsScreen(uiState = uiState, onAction = { actions += it }) }
        }

        composeTestRule.onNodeWithText(LanguageEnum.SPANISH.description).performClick()
        composeTestRule.onNodeWithContentDescription(context.getString(DesignSystemR.string.top_bar_back_icon)).performClick()

        actions shouldBe listOf(SettingsUiAction.OnLanguageSelected(LanguageEnum.SPANISH), SettingsUiAction.OnBack)
    }
}
