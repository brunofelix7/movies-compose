package dev.brunofelix.movies.core.presentation.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.core.presentation.util.shimmerEffect

/**
 * Loading placeholder for the movie and TV show detail screens. It mirrors the
 * [DetailHeader] geometry so the layout does not shift once the data arrives.
 *
 * @param infoChipCount how many info chips the screen shows below the title.
 */
@Composable
fun DetailSkeleton(
    modifier: Modifier = Modifier,
    infoChipCount: Int = 3
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Colors.blackPrimary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(BackdropHeight + PosterOverhang)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(BackdropHeight)
                    .align(Alignment.TopStart)
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(PosterWidthFraction)
                    .height(PosterHeight)
                    .align(Alignment.BottomStart)
                    .clip(RoundedCornerShape(12.dp))
                    .shimmerEffect()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7F)
                    .height(30.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(infoChipCount) {
                    Box(
                        modifier = Modifier
                            .size(60.dp, 24.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .shimmerEffect()
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .size(80.dp, 32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmerEffect()
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            repeat(5) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    DetailSkeleton()
}
