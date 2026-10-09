package dev.brunofelix.movies.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.shapeRounded32
import dev.brunofelix.movies.designsystem.theme.size1
import dev.brunofelix.movies.designsystem.theme.size20
import dev.brunofelix.movies.designsystem.theme.size48
import dev.brunofelix.movies.designsystem.theme.spacing12

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    isOutlined: Boolean = true
) {
    if (isOutlined) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(size48),
            border = BorderStroke(size1, MaterialTheme.colorScheme.primary),
            shape = shapeRounded32,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            ButtonContent(
                icon = icon,
                text = text
            )
        }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(size48),
            shape = shapeRounded32,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            ButtonContent(
                icon = icon,
                text = text
            )
        }
    }
}

@Composable
private fun ButtonContent(
    icon: ImageVector?,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing12)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(size20),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun OutlinedPreview() {
    PMovieTheme {
        CustomButton(
            text = "Watch Trailer",
            icon = Icons.Filled.Movie,
            isOutlined = true,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun FilledPreview() {
    PMovieTheme {
        CustomButton(
            text = "Save Selection",
            icon = Icons.Filled.Check,
            isOutlined = false,
            onClick = {}
        )
    }
}
