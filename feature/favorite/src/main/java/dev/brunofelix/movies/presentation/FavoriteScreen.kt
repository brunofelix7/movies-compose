package dev.brunofelix.movies.presentation

import android.widget.Toast
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.designsystem.components.EmptyState
import dev.brunofelix.movies.designsystem.components.GradientBackground
import dev.brunofelix.movies.designsystem.components.LoadingState
import dev.brunofelix.movies.designsystem.components.SelectorContentSpacing
import dev.brunofelix.movies.designsystem.components.SelectorTopSpacing
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.shapeRounded12
import dev.brunofelix.movies.designsystem.theme.spacing12
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing20
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.presentation.components.CategorySelector
import dev.brunofelix.movies.presentation.components.ErrorLayout
import dev.brunofelix.movies.presentation.model.MediaUiModel
import dev.brunofelix.movies.presentation.util.ObserveAsEvents
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.feature.favorite.R
import dev.brunofelix.movies.presentation.components.FavoriteItem
import dev.brunofelix.movies.presentation.model.FavoriteCategory

private const val DELETE_BACKGROUND_ALPHA = 0.8f

@Composable
internal fun FavoriteRoute(
    paddingValues: PaddingValues,
    onNavigateToDetails: (Media) -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is FavoriteUiEvent.NavigateToDetails -> onNavigateToDetails(event.media)
            is FavoriteUiEvent.ShowToast -> {
                Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
            }
        }
    }

    FavoriteScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        paddingValues = paddingValues
    )
}

@Composable
internal fun FavoriteScreen(
    uiState: FavoriteUiState,
    onAction: (FavoriteUiAction) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    // One state per category, otherwise every category lands on the scroll position of the
    // last one that was scrolled.
    val listStates = FavoriteCategory.entries.map { category ->
        key(category) { rememberLazyListState() }
    }
    val listState = listStates[uiState.selectedCategory.ordinal]

    val topInset = paddingValues.calculateTopPadding()
    val bottomInset = paddingValues.calculateBottomPadding()
    val medias = uiState.medias

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topInset)
        ) {
            CategorySelector(
                categories = FavoriteCategory.entries,
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { category ->
                    if (category is FavoriteCategory) {
                        onAction(FavoriteUiAction.OnCategorySelected(category))
                    }
                },
                modifier = Modifier.padding(top = SelectorTopSpacing)
            )

            if (medias is UiState.Success) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = spacing16, end = spacing16, top = SelectorContentSpacing),
                    verticalArrangement = Arrangement.spacedBy(spacing12),
                    contentPadding = PaddingValues(bottom = bottomInset + spacing16)
                ) {
                    items(
                        items = medias.data,
                        key = { item: MediaUiModel -> item.id }
                    ) { media ->
                        FavoriteSwipeItem(
                            media = media,
                            onDelete = { onAction(FavoriteUiAction.OnDelete(media)) },
                            onClick = { onAction(FavoriteUiAction.OnMediaClick(media)) }
                        )
                    }
                }
            }
        }

        // Centred against the whole content area rather than the space left below the selector,
        // which is what used to push these states under the middle of the screen.
        val stateModifier = Modifier.padding(top = topInset, bottom = bottomInset)

        when (medias) {
            is UiState.Loading -> LoadingState(modifier = stateModifier)
            is UiState.Error -> ErrorLayout(modifier = stateModifier, errorMessage = medias.uiText)
            is UiState.Empty -> EmptyState(
                modifier = stateModifier,
                message = stringResource(R.string.favorites_empty)
            )
            else -> Unit
        }
    }
}

@Composable
private fun FavoriteSwipeItem(
    media: MediaUiModel,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error.copy(alpha = DELETE_BACKGROUND_ALPHA)
                    else -> Color.Transparent
                },
                label = "DeleteAnimation"
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color, shape = shapeRounded12)
                    .padding(horizontal = spacing20),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        }
    ) {
        FavoriteItem(media = media, onClick = onClick)
    }
}

@Composable
private fun PreviewContainer(uiState: FavoriteUiState) {
    PMovieTheme {
        GradientBackground {
            FavoriteScreen(uiState = uiState, onAction = {})
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    PreviewContainer(FavoriteUiState(medias = UiState.Loading))
}

@Preview
@Composable
private fun SuccessPreview() {
    PreviewContainer(
        FavoriteUiState(
            medias = UiState.Success(
                listOf(
                    MediaUiModel(id = 1, title = "Movie 1", type = MediaType.MOVIE),
                    MediaUiModel(id = 2, title = "Movie 2", type = MediaType.MOVIE)
                )
            )
        )
    )
}

@Preview
@Composable
private fun EmptyPreview() {
    PreviewContainer(FavoriteUiState(selectedCategory = FavoriteCategory.TV_SHOWS, medias = UiState.Empty))
}

@Preview
@Composable
private fun ErrorPreview() {
    PreviewContainer(FavoriteUiState(medias = UiState.Error(UiText.DynamicString("Error"))))
}
