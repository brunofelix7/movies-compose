package dev.brunofelix.movies.core.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.presentation.navigation.AppDestination
import dev.brunofelix.movies.core.presentation.navigation.AppNavHost
import dev.brunofelix.movies.core.presentation.navigation.Navigator
import dev.brunofelix.movies.core.presentation.navigation.rememberNavigationState
import dev.brunofelix.movies.core.presentation.ui.components.CustomNavBar
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.MainTopBar
import dev.brunofelix.movies.core.presentation.util.extension.shouldShowBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState(
        startRoute = AppDestination.Movies,
        topLevelRoutes = setOf(
            AppDestination.Movies,
            AppDestination.TvShows,
            AppDestination.Search,
            AppDestination.Favorites
        )
    )
    val navigator = remember { Navigator(navigationState) }
    
    val currentRoute = navigationState.backStacks[navigationState.topLevelRoute]?.last()
    val isBottomBarVisible = currentRoute.shouldShowBottomBar
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    GradientBackground {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
            topBar = {
                if (isBottomBarVisible) {
                    MainTopBar(
                        scrollBehavior = scrollBehavior
                    )
                }
            },
            bottomBar = {
                BottomNavigationBarAnimated(
                    visible = isBottomBarVisible
                ) {
                    CustomNavBar(
                        navigationState = navigationState,
                        navigator = navigator
                    )
                }
            },
            content = { paddingValues ->
                AppNavHost(
                    paddingValues = paddingValues,
                    navigationState = navigationState,
                    navigator = navigator
                )
            }
        )
    }
}

@Composable
private fun BottomNavigationBarAnimated(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(600)),
        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(600)),
        content = { content() }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MainScreen()
}
