package dev.brunofelix.movies.feature.detail.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.brunofelix.movies.core.presentation.navigation.MovieDestination
import dev.brunofelix.movies.core.presentation.navigation.enterTransition
import dev.brunofelix.movies.core.presentation.navigation.exitTransition
import dev.brunofelix.movies.core.presentation.navigation.popEnterTransition
import dev.brunofelix.movies.core.presentation.navigation.popExitTransition
import dev.brunofelix.movies.feature.detail.presentation.ui.MovieDetailRoute

fun NavGraphBuilder.movieDetailGraph(
    navController: NavController
) {
    composable<MovieDestination.Details>(
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition
    ) { backStackEntry ->
        val movieId = backStackEntry.toRoute<MovieDestination.Details>().movieId

        MovieDetailRoute(
            movieId = movieId,
            navController = navController,
            backStackEntry = backStackEntry
        )
    }
}