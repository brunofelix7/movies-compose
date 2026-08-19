package dev.brunofelix.movies.feature.favorite.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.core.presentation.ui.components.EmptyState
import dev.brunofelix.movies.core.presentation.ui.components.ErrorLayout
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.core.presentation.ui.model.MediaUiModel
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

    FavoriteScreen(
        paddingValues = paddingValues,
        uiState = uiState,
        onCardClick = { id ->
            // TODO: navigate to movie or TV show details
            onNavigate(MainNavKey.MovieDetails(id))
        }
    )
}

@Composable
internal fun FavoriteScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<List<MediaUiModel>>,
    paddingValues: PaddingValues = PaddingValues(),
    onCardClick: (id: Long) -> Unit = {}
) {
    when (uiState) {
        is UiState.Loading -> LoadingState()
        is UiState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = paddingValues,
                content = {
                    items(
                        items = uiState.data,
                        key = { item: MediaUiModel -> item.id }
                    ) { media ->
                        FavoriteItem (
                            media = media,
                            onClick = { onCardClick(media.id) }
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