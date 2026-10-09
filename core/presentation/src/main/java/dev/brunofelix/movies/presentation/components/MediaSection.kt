package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.components.EmptyState
import dev.brunofelix.movies.designsystem.components.GradientBackground
import dev.brunofelix.movies.designsystem.components.LoadingState
import dev.brunofelix.movies.designsystem.components.PagingRetry
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.SurfaceGlass
import dev.brunofelix.movies.designsystem.theme.shapeRounded12
import dev.brunofelix.movies.designsystem.theme.size120
import dev.brunofelix.movies.designsystem.theme.size180
import dev.brunofelix.movies.designsystem.theme.size24
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing8
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText

/**
 * One horizontal row of the home screens, closed by a "View more" card that opens the full,
 * paginated version of the list.
 */
@Composable
fun MediaSection(
    title: String,
    state: UiState<List<Media>>,
    modifier: Modifier = Modifier,
    onItemClick: (Media) -> Unit = {},
    onViewMore: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = spacing16, end = spacing16, bottom = spacing8)
        )

        when (state) {
            is UiState.Initial, is UiState.Loading -> SectionBox { LoadingState() }
            is UiState.Error -> SectionBox { PagingRetry(onRetry = onRetry) }
            is UiState.Empty -> SectionBox { EmptyState() }
            is UiState.Success -> LazyRow(
                contentPadding = PaddingValues(horizontal = spacing16),
                horizontalArrangement = Arrangement.spacedBy(spacing8),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    items = state.data,
                    key = { media -> media.id }
                ) { media ->
                    MediaCard(
                        media = media,
                        onClick = { onItemClick(media) },
                        modifier = Modifier
                            .width(size120)
                            .height(size180)
                    )
                }
                item {
                    ViewMoreCard(onClick = onViewMore)
                }
            }
        }
    }
}

@Composable
private fun SectionBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(size180)
    ) {
        content()
    }
}

@Composable
private fun ViewMoreCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(size120)
            .height(size180)
            .clip(shapeRounded12)
            .background(SurfaceGlass)
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing8)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
                modifier = Modifier.size(size24)
            )
            Text(
                text = stringResource(R.string.view_more),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = spacing8)
            )
        }
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    PMovieTheme {
        GradientBackground {
            Box(modifier = Modifier.fillMaxSize()) {
                MediaSection(
                    title = "Popular",
                    state = UiState.Success(
                        listOf(
                            Media(id = 1L, title = "Movie 1"),
                            Media(id = 2L, title = "Movie 2"),
                            Media(id = 3L, title = "Movie 3")
                        )
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    PMovieTheme {
        GradientBackground {
            MediaSection(title = "Popular", state = UiState.Loading)
        }
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    PMovieTheme {
        GradientBackground {
            MediaSection(title = "Popular", state = UiState.Error(UiText.DynamicString("Error")))
        }
    }
}
