package dev.brunofelix.movies.core.presentation.ui.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.ui.navigation.MovieRoute

fun NavGraphBuilder.favoriteScreen(
    navController: NavHostController
) {
    composable<MovieRoute.FavoritesScreen> {
        // TODO: Add favorite screen
    }
}