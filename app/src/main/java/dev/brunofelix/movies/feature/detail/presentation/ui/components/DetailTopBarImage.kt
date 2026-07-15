package dev.brunofelix.movies.feature.detail.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.brunofelix.movies.core.presentation.ui.components.EmptyImage
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.feature.detail.presentation.state.DetailUiState

@Composable
fun DetailTopBarImage(
    uiState: DetailUiState,
    modifier: Modifier = Modifier
) {
    val backdropPath = remember { mutableStateOf<String?>("") }
    val shape = RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(Colors.blackPrimary),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(backdropPath.value)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35F)
                .align(Alignment.Center)
        )
        when (uiState.uiState) {
            is UiState.Loading -> LoadingState()
            is UiState.Success -> {
                backdropPath.value = uiState.uiState.data.backdropPath
                if (backdropPath.value?.isEmpty() == true) {
                    EmptyImage()
                }
            }
            else -> EmptyImage()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DetailTopBarImage(
        uiState = DetailUiState()
    )
}