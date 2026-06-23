package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@Composable
fun ErrorLayout(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(Colors.darkGray)
                .size(128.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_error_outline),
                contentDescription = stringResource(R.string.error_icon),
                tint = Colors.redPrimary,
                modifier = Modifier.size(64.dp),
            )
        }
        Spacer(Modifier.size(16.dp))
        Text(
            text = stringResource(R.string.error_title),
            fontSize = 22.sp,
            color = Colors.white,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = stringResource(R.string.error_message),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Colors.lightGray,
        )
        Spacer(Modifier.size(16.dp))
        CustomButton(
            text = stringResource(R.string.retry),
            isOutlined = false,
            modifier = Modifier.fillMaxWidth(0.35F)
        ) {

        }
    }
}

@Preview
@Composable
private fun ErrorLayoutPreview() {
    ErrorLayout()
}