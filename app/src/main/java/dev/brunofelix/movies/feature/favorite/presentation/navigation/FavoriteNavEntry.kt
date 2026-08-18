package dev.brunofelix.movies.feature.favorite.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.feature.favorite.presentation.ui.FavoriteRoute

fun EntryProviderScope<NavKey>.favoriteEntry(
    onNavigate: (MainNavKey) -> Unit,
    paddingValues: PaddingValues
) {
    entry<MainNavKey.Favorites> {
        FavoriteRoute(
            onNavigate = onNavigate,
            paddingValues = paddingValues
        )
    }
}