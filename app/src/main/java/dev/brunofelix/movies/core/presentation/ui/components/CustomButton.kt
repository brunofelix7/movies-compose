package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    isOutlined: Boolean = true,
    onClick: () -> Unit
) {
    if (isOutlined) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp),
            border = BorderStroke(1.dp, Colors.redPrimary),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Colors.redPrimary
            )
        ) {
            ButtonContent(icon = icon, text = text)
        }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Colors.redPrimary,
                contentColor = Colors.white
            )
        ) {
            ButtonContent(icon = icon, text = text)
        }
    }
}

@Composable
private fun ButtonContent(
    icon: ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Colors.white
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = Colors.white
        )
    }
}

@Preview
@Composable
private fun OutlinedPreview() {
    CustomButton(
        text = "Watch Trailer",
        icon = Icons.Filled.Movie,
        isOutlined = true
    ) {

    }
}

@Preview
@Composable
private fun FilledPreview() {
    CustomButton(
        text = "Save Selection",
        icon = Icons.Filled.Check,
        isOutlined = false
    ) {

    }
}