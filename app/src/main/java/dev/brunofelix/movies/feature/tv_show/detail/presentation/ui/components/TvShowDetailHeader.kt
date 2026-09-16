package dev.brunofelix.movies.feature.tv_show.detail.presentation.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.presentation.ui.components.MediaCard
import dev.brunofelix.movies.core.presentation.ui.model.TvShowUiModel
import kotlin.math.roundToInt

@Composable
fun TvShowDetailHeader(
    tvShow: TvShowUiModel?,
    isFavorite: Boolean,
    scrollState: ScrollState,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val initialOffsetPx = with(density) { 80.dp.toPx() }

    Box(
        modifier = modifier
    ) {
        TvShowDetailTopBarImage(
            backdropPath = tvShow?.backdropPath
        )
        TvShowDetailTopBar(
            isFavorite = isFavorite,
            shouldShowFavorite = tvShow != null,
            onBackClick = onBackClick,
            onFavoriteClick = onFavoriteClick
        )
        tvShow?.let {
            MediaCard(
                media = Media(
                    id = it.id,
                    title = it.name,
                    posterPath = it.posterPath,
                    releaseDate = it.firstAirDate,
                ),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(0.45F)
                    .height(220.dp)
                    .align(Alignment.BottomStart)
                    .offset {
                        val offset = (initialOffsetPx - scrollState.value).coerceAtLeast(0f)
                        IntOffset(0, offset.roundToInt())
                    }
            )
        }
    }
}
