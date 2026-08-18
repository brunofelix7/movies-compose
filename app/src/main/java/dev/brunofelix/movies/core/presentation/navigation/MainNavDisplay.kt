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
fun MainNavDisplay(
    backStack: List<MainNavKey>,
    onNavigate: (MainNavKey) -> Unit,
    onBack: () -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val entryProvider = entryProvider {
        // Movies Screen
        movieHomeEntry(onNavigate, paddingValues)

        // TV Shows Screen
        tvShowHomeEntry(onNavigate, paddingValues)

        // Search Screen
        searchEntry(onNavigate, paddingValues)

        // Favorites Screen
        favoriteEntry(onNavigate, paddingValues)

        // Movie Details Screen
        movieDetailEntry(onBack)

        // TV Show Details Screen
        tvShowDetailEntry(onBack)
    }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = onBack,
        entryProvider = entryProvider
    )
}
