package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.shapeRounded8

private const val VIDEO_ASPECT_RATIO = 16f / 9f

/**
 * Embedded YouTube player that cues [videoId] without playing it. Previews get a placeholder,
 * since the player view can't run inside the layout inspector.
 */
@Composable
fun YouTubePlayer(
    videoId: String,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    if (LocalInspectionMode.current) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(VIDEO_ASPECT_RATIO)
                .clip(shapeRounded8)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        return
    }

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .clip(shapeRounded8),
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                })
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        YouTubePlayer(videoId = "Way9Dexny3w")
    }
}
