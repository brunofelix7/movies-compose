package dev.brunofelix.movies.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            maxLines = 2,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Button(
            onClick = {
                onRetry()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Colors.redPrimary,
                contentColor = Color.White
            )
        ) {
            Text(text = "Retry")
        }
    }
}

@Preview
@Composable
fun ErrorViewPreview() {
    ErrorView(
        message = "Error",
        onRetry = { }
    )
}
