package dev.brunofelix.movies.core.presentation.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.core.presentation.navigation.MainNavDisplay
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.core.presentation.navigation.MainNavViewModel
import dev.brunofelix.movies.core.presentation.ui.components.CustomNavBar
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.MainTopBar
import dev.brunofelix.movies.core.presentation.util.extension.shouldShowBottomBar
import dev.brunofelix.movies.feature.search.presentation.ui.SearchOverlayRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainNavViewModel = hiltViewModel()
) {
    val backStack by viewModel.backStack.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val isSearchVisible by viewModel.isSearchVisible.collectAsStateWithLifecycle()

    MainScreenContent(
        backStack = backStack,
        currentTab = currentTab,
        isSearchVisible = isSearchVisible,
        onNavigate = viewModel::navigateTo,
        onBack = viewModel::popBackStack,
        onSearchVisibilityChange = viewModel::onSearchVisibilityChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    backStack: List<MainNavKey>,
    currentTab: MainNavKey,
    onNavigate: (MainNavKey) -> Unit,
    onBack: () -> Unit,
    isSearchVisible: Boolean = false,
    onSearchVisibilityChange: (Boolean) -> Unit = {}
) {
    val currentRoute = backStack.lastOrNull()
    val isBottomBarVisible = currentRoute?.shouldShowBottomBar ?: false
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val closeSearch = { onSearchVisibilityChange(false) }
    val navigateAndCloseSearch = { route: MainNavKey ->
        closeSearch()
        onNavigate(route)
    }

    GradientBackground {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
            topBar = {
                TopBarAnimated(
                    visible = isBottomBarVisible
                ) {
                    MainTopBar(
                        scrollBehavior = scrollBehavior,
                        isSearching = isSearchVisible,
                        onSearch = { onSearchVisibilityChange(true) },
                        onCancelSearch = closeSearch,
                        onSettings = { navigateAndCloseSearch(MainNavKey.Settings) }
                    )
                }
            },
            bottomBar = {
                BottomNavigationBarAnimated(
                    visible = isBottomBarVisible
                ) {
                    CustomNavBar(
                        currentTab = currentTab,
                        onNavigate = navigateAndCloseSearch
                    )
                }
            },
            content = { paddingValues ->
                Box(modifier = Modifier.fillMaxSize()) {
                    MainNavDisplay(
                        backStack = backStack,
                        onNavigate = onNavigate,
                        onBack = onBack,
                        paddingValues = paddingValues
                    )

                    SearchOverlayRoute(
                        isActive = isSearchVisible,
                        isVisible = isSearchVisible && isBottomBarVisible,
                        paddingValues = paddingValues,
                        onClose = closeSearch,
                        onNavigate = onNavigate,
                        modifier = Modifier.zIndex(1F)
                    )
                }
            }
        )
    }
}

@Composable
private fun TopBarAnimated(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { -it }, animationSpec = tween(600)),
        exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(600)),
        content = { content() }
    )
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
    MainScreenContent(
        backStack = listOf(MainNavKey.Movies),
        currentTab = MainNavKey.Movies,
        onNavigate = {},
        onBack = {}
    )
}
