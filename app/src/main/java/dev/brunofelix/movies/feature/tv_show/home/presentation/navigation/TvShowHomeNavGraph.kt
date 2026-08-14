package dev.brunofelix.movies.feature.tv_show.home.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.AppDestination
import dev.brunofelix.movies.core.presentation.navigation.Navigator
import dev.brunofelix.movies.feature.tv_show.home.presentation.ui.TvShowHomeScreen

fun EntryProviderScope<NavKey>.tvShowHomeGraph(
    navigator: Navigator,
    paddingValues: PaddingValues
) {
    entry<AppDestination.TvShows> {
        TvShowHomeScreen(
            onItemClick = { tvShowId ->
                navigator.navigate(AppDestination.Details(tvShowId))
            },
            paddingValues = paddingValues
        )
    }
}
