package dev.brunofelix.movies.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.theme.OutlineSubtle
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.ScrimLight
import dev.brunofelix.movies.designsystem.theme.shapeRounded16
import dev.brunofelix.movies.designsystem.theme.size1
import dev.brunofelix.movies.designsystem.theme.size30
import dev.brunofelix.movies.designsystem.theme.size5
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing8

@Composable
fun SectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = shapeRounded16,
        colors = CardDefaults.cardColors(
            containerColor = ScrimLight
        ),
        border = BorderStroke(size1, OutlineSubtle),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing16),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing16)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing8)
            ) {
                Box(
                    modifier = Modifier
                        .width(size5)
                        .height(size30)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            content()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        SectionCard(title = "Overview") {
            Text(
                text = "Lorem ipsum dolor sit amet.",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
