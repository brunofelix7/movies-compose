package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.brunofelix.movies.feature.detail.presentation.navigation.detailGraph
import dev.brunofelix.movies.feature.favorite.presentation.navigation.favoriteGraph
import dev.brunofelix.movies.feature.popular.presentation.navigation.movieGraph
import dev.brunofelix.movies.feature.search.presentation.navigation.searchGraph
import dev.brunofelix.movies.feature.upcoming.presentation.navigation.tvShowGraph

@Composable
fun AppNavHost(
    paddingValues: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier.padding(top = 12.dp),
        navController = navController,
        startDestination = AppDestination.Movies
    ) {
        // Movies Screen
        movieGraph(
            navController = navController,
            paddingValues = paddingValues
        )

        // TV Shows Screen
        tvShowGraph(
            navController = navController,
            paddingValues = paddingValues
        )

        // Search Screen
        searchGraph(
            navController = navController,
            paddingValues = paddingValues
        )

        // Details Screen
        detailGraph(
            navController = navController
        )

        // Favorites Screen
        favoriteGraph(
            navController = navController,
            paddingValues = paddingValues
        )
    }
}
