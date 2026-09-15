package dev.brunofelix.movies.feature.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.feature.settings.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.feature.settings.domain.use_case.SaveLanguageUseCase
import dev.brunofelix.movies.feature.settings.presentation.state.SettingsState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getLanguageUseCase: GetLanguageUseCase,
    private val saveLanguageUseCase: SaveLanguageUseCase
) : ViewModel() {

    val state: StateFlow<SettingsState> = getLanguageUseCase()
        .map { language -> SettingsState(selectedLanguage = language) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsState()
        )

    fun onLanguageSelected(language: LanguageEnum) = viewModelScope.launch {
        saveLanguageUseCase(language)
    }
}
