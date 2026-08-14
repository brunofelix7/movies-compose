package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.brunofelix.movies.feature.favorite.presentation.navigation.favoriteEntry
import dev.brunofelix.movies.feature.movie.detail.presentation.navigation.movieDetailEntry
import dev.brunofelix.movies.feature.movie.home.presentation.navigation.movieHomeEntry
import dev.brunofelix.movies.feature.search.presentation.navigation.searchEntry
import dev.brunofelix.movies.feature.tv_show.detail.presentation.navigation.tvShowDetailEntry
import dev.brunofelix.movies.feature.tv_show.home.presentation.navigation.tvShowHomeEntry

@Composable
fun MainNavHost(
    paddingValues: PaddingValues,
    navigationState: NavigationState,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val entryProvider = entryProvider {
        // Movies Screen
        movieHomeEntry(
            navigator = navigator,
            paddingValues = paddingValues
        )

        // TV Shows Screen
        tvShowHomeEntry(
            navigator = navigator,
            paddingValues = paddingValues
        )

        // Search Screen
        searchEntry(
            paddingValues = paddingValues
        )

        // Favorites Screen
        favoriteEntry(
            navigator = navigator,
            paddingValues = paddingValues
        )

        // Movie Details Screen
        movieDetailEntry(
            navigator = navigator
        )

        // TV Show Details Screen
        tvShowDetailEntry(
            navigator = navigator
        )
    }

    NavDisplay(
        modifier = modifier,
        entries = navigationState.toEntries(entryProvider),
        onBack = { navigator.goBack() }
    )
}
