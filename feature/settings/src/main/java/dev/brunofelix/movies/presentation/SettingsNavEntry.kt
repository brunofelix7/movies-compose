package dev.brunofelix.movies.presentation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.presentation.navigation.Route

fun EntryProviderScope<NavKey>.settingsNavEntry(
    onBack: () -> Unit
) {
    entry<Route.Settings> {
        SettingsRoute(onBack = onBack)
    }
}
