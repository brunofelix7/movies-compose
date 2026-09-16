package dev.brunofelix.movies.feature.media_list.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.MainContent
import dev.brunofelix.movies.core.presentation.ui.components.SecondaryTopBar
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme
import dev.brunofelix.movies.core.presentation.util.extension.collectAsPreviewLazyPagingItems
import dev.brunofelix.movies.feature.media_list.presentation.viewmodel.MediaListViewModel

@Composable
fun MediaListRoute(
    category: MediaListCategory,
    month: ReleaseMonth?,
    onItemClick: (id: Long) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MediaListViewModel = hiltViewModel()
) {
    LaunchedEffect(category, month) {
        viewModel.load(category, month)
    }

    val medias = viewModel.medias.collectAsLazyPagingItems()

    MediaListScreen(
        title = stringResource(category.titleResId),
        medias = medias,
        onItemClick = onItemClick,
        onBack = onBack,
        modifier = modifier
    )
}

@Composable
private fun MediaListScreen(
    title: String,
    medias: LazyPagingItems<Media>,
    modifier: Modifier = Modifier,
    onItemClick: (id: Long) -> Unit = {},
    onBack: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            SecondaryTopBar(
                title = title,
                onBack = onBack
            )
        }
    ) { innerPadding ->
        MainContent(
            paging = medias,
            paddingValues = PaddingValues(bottom = 16.dp),
            onClick = onItemClick,
            media = { it },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        GradientBackground {
            MediaListScreen(
                title = "Top Rated",
                medias = listOf(
                    Media(id = 1L, title = "Movie 1"),
                    Media(id = 2L, title = "Movie 2")
                ).collectAsPreviewLazyPagingItems()
            )
        }
    }
}
