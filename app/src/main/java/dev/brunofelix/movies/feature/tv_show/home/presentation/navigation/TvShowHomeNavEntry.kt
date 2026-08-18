package dev.brunofelix.movies.feature.tv_show.home.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.feature.tv_show.home.presentation.ui.TvShowHomeScreen

fun EntryProviderScope<NavKey>.tvShowHomeEntry(
    onNavigate: (MainNavKey) -> Unit,
    paddingValues: PaddingValues
) {
    entry<MainNavKey.TvShows> {
        TvShowHomeScreen(
            onItemClick = { tvShowId ->
                onNavigate(MainNavKey.TvShowDetails(tvShowId))
            },
            paddingValues = paddingValues
        )
    }
}
