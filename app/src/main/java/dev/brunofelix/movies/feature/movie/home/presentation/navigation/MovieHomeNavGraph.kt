package dev.brunofelix.movies.feature.movie.home.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.navigation.AppDestination
import dev.brunofelix.movies.feature.movie.home.presentation.ui.MovieHomeScreen

fun NavGraphBuilder.movieHomeGraph(
    navController: NavController,
    paddingValues: PaddingValues
) {
    composable<AppDestination.Movies> {
        MovieHomeScreen(
            onItemClick = { movieId ->
                navController.navigate(AppDestination.Details(movieId))
            },
            paddingValues = paddingValues
        )
    }
}
