package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.components.CustomButton
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.shapeCircle
import dev.brunofelix.movies.designsystem.theme.size128
import dev.brunofelix.movies.designsystem.theme.size64
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing4
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR

private const val RETRY_BUTTON_WIDTH_FRACTION = 0.35f

/**
 * Full screen error state.
 *
 * @param onRetry action behind the retry button. Leave it null on screens that have nothing to
 * retry, and the button is left out instead of sitting there doing nothing.
 */
@Composable
fun ErrorLayout(
    modifier: Modifier = Modifier,
    errorMessage: UiText? = null,
    onRetry: (() -> Unit)? = null
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing16)
    ) {
        Box(
            modifier = Modifier
                .clip(shapeCircle)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .size(size128),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_error_outline),
                contentDescription = stringResource(R.string.error_icon),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(size64)
            )
        }
        Spacer(Modifier.size(spacing16))
        Text(
            text = stringResource(R.string.error_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = spacing4)
        )
        Text(
            text = errorMessage?.asString(context) ?: stringResource(R.string.error_message),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        onRetry?.let { retry ->
            Spacer(Modifier.size(spacing16))
            CustomButton(
                text = stringResource(DesignSystemR.string.retry),
                isOutlined = false,
                modifier = Modifier.fillMaxWidth(RETRY_BUTTON_WIDTH_FRACTION),
                onClick = retry
            )
        }
    }
}

@Preview
@Composable
private fun ErrorLayoutPreview() {
    PMovieTheme {
        ErrorLayout(onRetry = {})
    }
}

@Preview
@Composable
private fun NoRetryPreview() {
    PMovieTheme {
        ErrorLayout(errorMessage = UiText.DynamicString("No internet connection"))
    }
}
