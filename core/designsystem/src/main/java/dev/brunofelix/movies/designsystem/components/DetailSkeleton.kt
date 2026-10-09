package dev.brunofelix.movies.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.shapeRounded16
import dev.brunofelix.movies.designsystem.theme.shapeRounded2
import dev.brunofelix.movies.designsystem.theme.shapeRounded4
import dev.brunofelix.movies.designsystem.theme.shapeRounded8
import dev.brunofelix.movies.designsystem.theme.size16
import dev.brunofelix.movies.designsystem.theme.size24
import dev.brunofelix.movies.designsystem.theme.size30
import dev.brunofelix.movies.designsystem.theme.size32
import dev.brunofelix.movies.designsystem.theme.size60
import dev.brunofelix.movies.designsystem.theme.size80
import dev.brunofelix.movies.designsystem.theme.spacing12
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing8

const val DETAIL_SKELETON_TEST_TAG = "detail_skeleton"

private const val BACKDROP_SCREEN_FRACTION = 0.75f
private const val BACKDROP_VISIBLE_FRACTION = 0.6f
private const val BACKDROP_FADE_FRACTION = 0.4f
private const val TITLE_WIDTH_FRACTION = 0.7f
private const val GENRE_PLACEHOLDER_COUNT = 2
private const val OVERVIEW_LINE_COUNT = 5

@Composable
fun DetailSkeleton(
    modifier: Modifier = Modifier,
    infoChipCount: Int = 3
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = screenHeight * BACKDROP_SCREEN_FRACTION
    val background = MaterialTheme.colorScheme.background

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(background)
            .testTag(DETAIL_SKELETON_TEST_TAG)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .shimmerEffect()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(imageHeight * BACKDROP_VISIBLE_FRACTION))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight * BACKDROP_FADE_FRACTION)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, background)
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background)
                    .padding(horizontal = spacing16)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(TITLE_WIDTH_FRACTION)
                        .height(size30)
                        .clip(shapeRounded4)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(spacing12))
                Row(horizontalArrangement = Arrangement.spacedBy(spacing8)) {
                    repeat(infoChipCount) {
                        Box(
                            modifier = Modifier
                                .size(size60, size24)
                                .clip(shapeRounded16)
                                .shimmerEffect()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(spacing16))
                Row(horizontalArrangement = Arrangement.spacedBy(spacing8)) {
                    repeat(GENRE_PLACEHOLDER_COUNT) {
                        Box(
                            modifier = Modifier
                                .size(size80, size32)
                                .clip(shapeRounded8)
                                .shimmerEffect()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(spacing16))
                repeat(OVERVIEW_LINE_COUNT) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(size16)
                            .clip(shapeRounded2)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(spacing8))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PMovieTheme {
        DetailSkeleton()
    }
}
