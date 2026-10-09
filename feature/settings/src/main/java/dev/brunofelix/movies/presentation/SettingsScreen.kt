package dev.brunofelix.movies.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.designsystem.components.GradientBackground
import dev.brunofelix.movies.designsystem.components.SecondaryTopBar
import dev.brunofelix.movies.designsystem.components.SectionCard
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.shapeRounded8
import dev.brunofelix.movies.designsystem.theme.size1
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing4
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.presentation.util.ObserveAsEvents
import dev.brunofelix.movies.feature.settings.R

private const val DIVIDER_ALPHA = 0.2f

@Composable
internal fun SettingsRoute(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            SettingsUiEvent.NavigateBack -> onBack()
            is SettingsUiEvent.ShowToast -> {
                Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
            }
        }
    }

    SettingsScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun SettingsScreen(
    uiState: SettingsUiState,
    onAction: (SettingsUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            SecondaryTopBar(
                title = stringResource(R.string.settings),
                onBack = { onAction(SettingsUiAction.OnBack) }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing16),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(all = spacing16)
        ) {
            SectionCard(title = stringResource(R.string.settings_language)) {
                uiState.languages.forEachIndexed { index, language ->
                    LanguageItem(
                        language = language,
                        isSelected = language == uiState.selectedLanguage,
                        onClick = { onAction(SettingsUiAction.OnLanguageSelected(language)) }
                    )
                    if (index != uiState.languages.lastIndex) {
                        SettingsDivider()
                    }
                }
            }

            SectionCard(title = stringResource(R.string.settings_about)) {
                SettingsInfoRow(
                    label = stringResource(R.string.settings_version),
                    value = uiState.appVersion
                )
                SettingsDivider()
                Text(
                    text = stringResource(R.string.settings_data_source),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
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
            .clip(shapeRounded8)
            .clickable(onClick = onClick)
            .padding(vertical = spacing4)
    ) {
        Text(
            text = language.description,
            color = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                tint = MaterialTheme.colorScheme.primary,
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
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun SettingsDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = size1,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = DIVIDER_ALPHA)
    )
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        GradientBackground {
            SettingsScreen(
                uiState = SettingsUiState(selectedLanguage = LanguageEnum.PORTUGUESE, appVersion = "1.0.1"),
                onAction = {}
            )
        }
    }
}
