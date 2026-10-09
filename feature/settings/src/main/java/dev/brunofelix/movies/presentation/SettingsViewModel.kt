package dev.brunofelix.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.use_case.GetAppVersionUseCase
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.domain.use_case.SaveLanguageUseCase
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.feature.settings.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val STOP_TIMEOUT_MILLIS = 5_000L

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getLanguageUseCase: GetLanguageUseCase,
    getAppVersionUseCase: GetAppVersionUseCase,
    private val saveLanguageUseCase: SaveLanguageUseCase
) : ViewModel() {

    private val appVersion = getAppVersionUseCase()

    val uiState: StateFlow<SettingsUiState> = getLanguageUseCase()
        .map { language -> SettingsUiState(selectedLanguage = language, appVersion = appVersion) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = SettingsUiState(appVersion = appVersion)
        )

    private val _uiEvent = Channel<SettingsUiEvent>()
    val uiEvent: Flow<SettingsUiEvent> = _uiEvent.receiveAsFlow()

    fun onAction(action: SettingsUiAction) {
        when (action) {
            SettingsUiAction.OnBack -> viewModelScope.launch { _uiEvent.send(SettingsUiEvent.NavigateBack) }
            is SettingsUiAction.OnLanguageSelected -> saveLanguage(action.language)
        }
    }

    private fun saveLanguage(language: LanguageEnum) {
        viewModelScope.launch {
            if (saveLanguageUseCase(language) is Resource.Error) {
                _uiEvent.send(SettingsUiEvent.ShowToast(UiText.StringResource(R.string.settings_language_error)))
            }
        }
    }
}
