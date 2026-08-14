package dev.brunofelix.movies.feature.tv_show.home.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import dev.brunofelix.movies.core.domain.model.enums.TvShowCategory
import dev.brunofelix.movies.core.presentation.mapper.toUiState
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.ui.components.CategorySelector
import dev.brunofelix.movies.core.presentation.ui.components.MainContent
import dev.brunofelix.movies.feature.tv_show.home.presentation.viewmodel.TvShowHomeViewModel

@Composable
fun TvShowHomeScreen(
    onItemClick: (id: Long) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: TvShowHomeViewModel = hiltViewModel()
) {
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val tvShows = viewModel.tvShows.collectAsLazyPagingItems()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        CategorySelector(
            categories = TvShowCategory.entries,
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                if (category is TvShowCategory) {
                    viewModel.onCategorySelected(category)
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        AnimatedContent(
            targetState = selectedCategory,
            transitionSpec = {
                fadeIn().togetherWith(fadeOut())
            },
            label = "TvShowCategoryTransition"
        ) { targetCategory ->
            key(targetCategory) {
                MainContent(
                    paging = tvShows,
                    paddingValues = PaddingValues(bottom = paddingValues.calculateBottomPadding()),
                    onClick = onItemClick,
                    toUiState = { tvShow ->
                        val tvState = tvShow.toUiState()
                        // Map TvShowUiState to MovieUiState (which is what MovieCard currently uses)
                        // In a real project, we'd have a unified CardUiState
                        MovieUiState(
                            id = tvState.id,
                            title = tvState.name,
                            posterPath = tvState.posterPath,
                            backdropPath = tvState.backdropPath,
                            voteAverage = tvState.voteAverage
                        )
                    }
                )
            }
        }
    }
}
