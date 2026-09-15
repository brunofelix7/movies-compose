package dev.brunofelix.movies.feature.settings.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.feature.settings.presentation.ui.SettingsRoute

fun EntryProviderScope<NavKey>.settingsEntry(
    paddingValues: PaddingValues
) {
    entry<MainNavKey.Settings> {
        SettingsRoute(
            paddingValues = paddingValues
        )
    }
}
