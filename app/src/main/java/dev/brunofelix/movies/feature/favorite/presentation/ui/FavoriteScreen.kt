package dev.brunofelix.movies.feature.favorite.presentation.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.core.domain.model.enums.FavoriteCategory
import dev.brunofelix.movies.core.domain.model.enums.MediaType
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.core.presentation.ui.components.CategorySelector
import dev.brunofelix.movies.core.presentation.ui.components.EmptyState
import dev.brunofelix.movies.core.presentation.ui.components.ErrorLayout
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.core.presentation.ui.model.MediaUiModel
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.core.presentation.util.UiText
import dev.brunofelix.movies.feature.favorite.presentation.ui.components.FavoriteItem

@Composable
internal fun FavoriteRoute(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onNavigate: (MainNavKey) -> Unit,
    paddingValues: PaddingValues
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

    FavoriteScreen(
        paddingValues = paddingValues,
        uiState = uiState,
        selectedCategory = selectedCategory,
        onCategorySelected = viewModel::onCategorySelected,
        onDelete = viewModel::onDeleteFavorite,
        onCardClick = { media ->
            if (media.type == MediaType.TV_SHOW) {
                onNavigate(MainNavKey.TvShowDetails(media.id))
            } else {
                onNavigate(MainNavKey.MovieDetails(media.id))
            }
        }
    )
}

@Composable
internal fun FavoriteScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<List<MediaUiModel>>,
    selectedCategory: FavoriteCategory = FavoriteCategory.MOVIES,
    onCategorySelected: (FavoriteCategory) -> Unit = {},
    onDelete: (MediaUiModel) -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(),
    onCardClick: (media: MediaUiModel) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        CategorySelector(
            categories = FavoriteCategory.entries,
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                if (category is FavoriteCategory) {
                    onCategorySelected(category)
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            thickness = 1.dp,
            color = Colors.lightGray.copy(alpha = 0.2f)
        )

        when (uiState) {
            is UiState.Loading -> LoadingState()
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding() + 16.dp),
                    content = {
                        items(
                            items = uiState.data,
                            key = { item: MediaUiModel -> item.id }
                        ) { media ->
                            val dismissState = rememberSwipeToDismissBoxState(
                                confirmValueChange = {
                                    if (it == SwipeToDismissBoxValue.EndToStart) {
                                        onDelete(media)
                                        true
                                    } else false
                                }
                            )

                            SwipeToDismissBox(
                                state = dismissState,
                                enableDismissFromStartToEnd = false,
                                backgroundContent = {
                                    val color by animateColorAsState(
                                        when (dismissState.targetValue) {
                                            SwipeToDismissBoxValue.EndToStart -> Color.Red.copy(alpha = 0.8f)
                                            else -> Color.Transparent
                                        }, label = "DeleteAnimation"
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color, shape = RoundedCornerShape(12.dp))
                                            .padding(horizontal = 20.dp),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                },
                                content = {
                                    FavoriteItem(
                                        media = media,
                                        onClick = { onCardClick(media) }
                                    )
                                }
                            )
                        }
                    }
                )
            }
            is UiState.Error -> ErrorLayout()
            is UiState.Empty -> EmptyState()
            else -> Unit
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    FavoriteScreen(
        uiState = UiState.Loading
    )
}

@Preview
@Composable
private fun SuccessPreview() {
    FavoriteScreen(
        uiState = UiState.Success(
            data = listOf(
                MediaUiModel(id = 1, title = "Movie 1", posterPath = ""),
                MediaUiModel(id = 2, title = "Movie 2", posterPath = "")
            )
        )
    )
}

@Preview
@Composable
private fun ErrorPreview() {
    FavoriteScreen(
        uiState = UiState.Error(UiText.DynamicString("Error"))
    )
}