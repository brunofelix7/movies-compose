package dev.brunofelix.movies.feature.search.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.navigation.AppDestination
import dev.brunofelix.movies.feature.search.presentation.ui.MovieSearchScreen

fun NavGraphBuilder.searchGraph(
    navController: NavController,
    paddingValues: PaddingValues
) {
    composable<AppDestination.Search> {
        MovieSearchScreen(
            paddingValues = paddingValues
        )
    }
}
