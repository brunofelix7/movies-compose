package dev.brunofelix.movies.feature.favorite.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.brunofelix.movies.core.presentation.ui.components.MovieInfoChip
import dev.brunofelix.movies.core.presentation.ui.model.MediaUiModel
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

@Composable
fun FavoriteItem(
    modifier: Modifier = Modifier,
    media: MediaUiModel,
    onClick: (id: Long) -> Unit = {}
) {
    val shape = RoundedCornerShape(12.dp)

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Colors.blackSecondary
        ),
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(
                onClick = { onClick(media.id) }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(media.posterPath)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.weight(0.3F)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(0.7F)
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = media.title,
                    maxLines = 2,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    color = Colors.white
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MovieInfoChip(
                        icon = Icons.Outlined.CalendarMonth,
                        text = media.releaseDate
                    )
                    MovieInfoChip(
                        icon = Icons.Outlined.Timer,
                        text = media.duration
                    )
                }
                MovieInfoChip(
                    icon = Icons.Default.Star,
                    iconTint = Color.Yellow,
                    text = media.voteAverage
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.LightGray
            )
            Spacer(Modifier.width(2.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SuccessPreview() {
    FavoriteItem(
        media = MediaUiModel(
            id = 1,
            title = "Title"
        )
    )
}