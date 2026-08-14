package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.brunofelix.movies.feature.favorite.presentation.navigation.favoriteGraph
import dev.brunofelix.movies.feature.movie.detail.presentation.navigation.detailGraph
import dev.brunofelix.movies.feature.movie.home.presentation.navigation.movieHomeGraph
import dev.brunofelix.movies.feature.search.presentation.navigation.searchGraph
import dev.brunofelix.movies.feature.tv_show.home.presentation.navigation.tvShowHomeGraph

@Composable
fun AppNavHost(
    paddingValues: PaddingValues,
    navigationState: NavigationState,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val entryProvider = entryProvider {
        // Movies Screen
        movieHomeGraph(
            navigator = navigator,
            paddingValues = paddingValues
        )

        // TV Shows Screen
        tvShowHomeGraph(
            navigator = navigator,
            paddingValues = paddingValues
        )

        // Search Screen
        searchGraph(
            paddingValues = paddingValues
        )

        // Favorites Screen
        favoriteGraph(
            navigator = navigator,
            paddingValues = paddingValues
        )

        // Movie Details Screen
        detailGraph(
            navigator = navigator
        )
    }

    NavDisplay(
        modifier = modifier,
        entries = navigationState.toEntries(entryProvider),
        onBack = { navigator.goBack() }
    )
}
