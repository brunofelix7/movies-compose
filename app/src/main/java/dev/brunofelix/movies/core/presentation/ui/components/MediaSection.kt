package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme
import dev.brunofelix.movies.core.presentation.util.UiState

private val CardWidth = 120.dp
private val CardHeight = 180.dp

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
            color = Colors.white,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
        )

        when (state) {
            is UiState.Initial, is UiState.Loading -> SectionBox { LoadingState() }
            is UiState.Error -> SectionBox { PagingRetry(onRetry = onRetry) }
            is UiState.Empty -> SectionBox { EmptyState() }
            is UiState.Success -> LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                            .width(CardWidth)
                            .height(CardHeight)
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
            .height(CardHeight)
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
            .width(CardWidth)
            .height(CardHeight)
            .clip(RoundedCornerShape(12.dp))
            .background(Colors.white.copy(alpha = 0.08F))
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                tint = Colors.white,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = stringResource(R.string.view_more),
                color = Colors.white,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
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
