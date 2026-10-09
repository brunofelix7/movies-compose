package dev.brunofelix.movies.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.R
import dev.brunofelix.movies.designsystem.components.GradientBackground
import dev.brunofelix.movies.designsystem.components.MainTopBar
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.presentation.components.CustomNavBar
import dev.brunofelix.movies.presentation.navigation.Route
import dev.brunofelix.movies.presentation.navigation.isTopLevel
import dev.brunofelix.movies.presentation.navigation.toDetailRoute
import dev.brunofelix.movies.presentation.viewmodel.NavigationViewModel
import dev.brunofelix.movies.presentation.SearchOverlayRoute
import dev.brunofelix.movies.navigation.NavigationGraph

private const val BARS_ANIMATION_MILLIS = 600

/**
 * App shell: the tabs' top bar, bottom bar and search overlay around the navigation graph.
 */
@Composable
fun MainScreen(
    viewModel: NavigationViewModel = hiltViewModel()
) {
    val backStack by viewModel.backStack.collectAsStateWithLifecycle()
    var isSearchVisible by rememberSaveable { mutableStateOf(false) }

    MainScreenContent(
        backStack = backStack,
        isSearchVisible = isSearchVisible,
        onNavigate = viewModel::navigateTo,
        onReplace = viewModel::replaceCurrent,
        onBack = viewModel::popBackStack,
        onSearchVisibilityChange = { isSearchVisible = it }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    backStack: List<Route>,
    onNavigate: (Route) -> Unit,
    onReplace: (Route) -> Unit,
    onBack: () -> Unit,
    isSearchVisible: Boolean = false,
    onSearchVisibilityChange: (Boolean) -> Unit = {}
) {
    val currentRoute = backStack.lastOrNull()
    val areBarsVisible = currentRoute.isTopLevel
    val currentTab = backStack.lastOrNull { it.isTopLevel } ?: Route.Movies
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val closeSearch = { onSearchVisibilityChange(false) }

    GradientBackground {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
            topBar = {
                TopBarAnimated(visible = areBarsVisible) {
                    MainTopBar(
                        title = stringResource(R.string.app_title),
                        scrollBehavior = scrollBehavior,
                        isSearching = isSearchVisible,
                        onSearch = { onSearchVisibilityChange(true) },
                        onCancelSearch = closeSearch,
                        onSettings = {
                            closeSearch()
                            onNavigate(Route.Settings)
                        }
                    )
                }
            },
            bottomBar = {
                BottomBarAnimated(visible = areBarsVisible) {
                    CustomNavBar(
                        currentTab = currentTab,
                        onNavigate = { tab ->
                            closeSearch()
                            // The bar only shows over a tab root, so switching tabs replaces it.
                            onReplace(tab)
                        }
                    )
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                NavigationGraph(
                    backStack = backStack,
                    onNavigate = onNavigate,
                    onReplace = onReplace,
                    onBack = onBack,
                    paddingValues = paddingValues
                )

                SearchOverlayRoute(
                    isActive = isSearchVisible,
                    isVisible = isSearchVisible && areBarsVisible,
                    paddingValues = paddingValues,
                    onClose = closeSearch,
                    onNavigateToDetails = { media -> onNavigate(media.toDetailRoute()) },
                    modifier = Modifier.zIndex(1f)
                )
            }
        }
    }
}

@Composable
private fun TopBarAnimated(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { -it }, animationSpec = tween(BARS_ANIMATION_MILLIS)),
        exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(BARS_ANIMATION_MILLIS))
    ) {
        content()
    }
}

@Composable
private fun BottomBarAnimated(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(BARS_ANIMATION_MILLIS)),
        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(BARS_ANIMATION_MILLIS))
    ) {
        content()
    }
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        MainScreenContent(
            backStack = listOf(Route.Settings),
            onNavigate = {},
            onReplace = {},
            onBack = {}
        )
    }
}
