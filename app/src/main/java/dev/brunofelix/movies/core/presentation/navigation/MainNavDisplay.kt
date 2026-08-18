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
        movieHomeEntry(
            onNavigate = onNavigate,
            paddingValues = paddingValues
        )

        // TV Shows Screen
        tvShowHomeEntry(
            onNavigate = onNavigate,
            paddingValues = paddingValues
        )

        // Search Screen
        searchEntry(
            onNavigate = onNavigate,
            paddingValues = paddingValues
        )

        // Favorites Screen
        favoriteEntry(
            onNavigate = onNavigate,
            paddingValues = paddingValues
        )

        // Movie Details Screen
        movieDetailEntry(
            onBack = onBack
        )

        // TV Show Details Screen
        tvShowDetailEntry(
            onNavigate = onNavigate
        )
    }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = onBack,
        entryProvider = entryProvider
    )
}
