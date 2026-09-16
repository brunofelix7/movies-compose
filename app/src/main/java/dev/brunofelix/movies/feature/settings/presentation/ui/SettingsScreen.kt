package dev.brunofelix.movies.feature.settings.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.SectionCard
import dev.brunofelix.movies.core.presentation.ui.components.SecondaryTopBar
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme
import dev.brunofelix.movies.feature.settings.presentation.state.SettingsState
import dev.brunofelix.movies.feature.settings.presentation.viewmodel.SettingsViewModel

@Composable
internal fun SettingsRoute(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onBack = onBack,
        onLanguageSelected = viewModel::onLanguageSelected
    )
}

@Composable
internal fun SettingsScreen(
    state: SettingsState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onLanguageSelected: (LanguageEnum) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            SecondaryTopBar(
                title = stringResource(R.string.settings),
                onBack = onBack
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp)
        ) {
            SectionCard(title = stringResource(R.string.settings_language)) {
                state.languages.forEachIndexed { index, language ->
                    LanguageItem(
                        language = language,
                        isSelected = language == state.selectedLanguage,
                        onClick = { onLanguageSelected(language) }
                    )
                    if (index != state.languages.lastIndex) {
                        SettingsDivider()
                    }
                }
            }

            SectionCard(title = stringResource(R.string.settings_about)) {
                SettingsInfoRow(
                    label = stringResource(R.string.settings_version),
                    value = state.appVersion
                )
                SettingsDivider()
                Text(
                    text = stringResource(R.string.settings_data_source),
                    color = Colors.lightGray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun LanguageItem(
    language: LanguageEnum,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = language.description,
            color = if (isSelected) Colors.white else Colors.lightGray,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1F)
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                tint = Colors.redPrimary,
                contentDescription = stringResource(R.string.settings_selected_language_icon)
            )
        }
    }
}

@Composable
private fun SettingsInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = Colors.white,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1F)
        )
        Text(
            text = value,
            color = Colors.lightGray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun SettingsDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 1.dp,
        color = Colors.lightGray.copy(alpha = 0.2F)
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    PMovieTheme {
        GradientBackground {
            SettingsScreen(
                state = SettingsState(selectedLanguage = LanguageEnum.PORTUGUESE)
            )
        }
    }
}
