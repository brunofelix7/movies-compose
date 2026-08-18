package dev.brunofelix.movies.feature.movie.home.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import dev.brunofelix.movies.core.domain.mapper.toMedia
import dev.brunofelix.movies.core.domain.model.enums.MovieCategory
import dev.brunofelix.movies.core.presentation.ui.components.CategorySelector
import dev.brunofelix.movies.core.presentation.ui.components.MainContent
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.feature.movie.home.presentation.viewmodel.MovieHomeViewModel

@Composable
fun MovieHomeScreen(
    onItemClick: (id: Long) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: MovieHomeViewModel = hiltViewModel()
) {
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val movies = viewModel.movies.collectAsLazyPagingItems()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        CategorySelector(
            categories = MovieCategory.entries,
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                if (category is MovieCategory) {
                    viewModel.onCategorySelected(category)
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            thickness = 1.dp,
            color = Colors.lightGray.copy(alpha = 0.2f)
        )

        AnimatedContent(
            targetState = selectedCategory,
            transitionSpec = {
                fadeIn().togetherWith(fadeOut())
            },
            label = "MovieCategoryTransition"
        ) { targetCategory ->
            key(targetCategory) {
                MainContent(
                    paging = movies,
                    paddingValues = PaddingValues(bottom = paddingValues.calculateBottomPadding()),
                    onClick = onItemClick,
                    media = { it.toMedia() }
                )
            }
        }
    }
}
