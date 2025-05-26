package dev.brunofelix.movies.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.resources.Colors

@Composable
fun EmptyData(
    message: String?,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.error_title),
            fontSize = 22.sp,
            color = Colors.white,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = message ?: stringResource(R.string.error_message),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Colors.lightGray,
        )
    }
}

@Preview
@Composable
private fun EmptyDataPreview() {
    EmptyData("Please, try again!")
}