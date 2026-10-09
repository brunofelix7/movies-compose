package dev.brunofelix.movies.presentation.mapper

import dev.brunofelix.movies.domain.model.Video

private const val SITE_YOUTUBE = "YouTube"
private const val TYPE_TRAILER = "Trailer"

/**
 * Key of the video to embed: the first YouTube trailer, or any YouTube video when there is no
 * trailer. Null when nothing can be played.
 */
fun List<Video>.toTrailerKey(): String? {
    return find { it.site.equals(SITE_YOUTUBE, ignoreCase = true) && it.type.equals(TYPE_TRAILER, ignoreCase = true) }?.key
        ?: firstOrNull { it.site.equals(SITE_YOUTUBE, ignoreCase = true) }?.key
}
