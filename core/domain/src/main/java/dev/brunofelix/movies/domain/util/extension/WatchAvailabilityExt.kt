package dev.brunofelix.movies.domain.util.extension

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.WatchAvailability
import dev.brunofelix.movies.domain.model.WatchProvider
import java.time.LocalDate

/**
 * Days after its premiere during which a movie that no streaming service offers yet is
 * considered to be in theaters. TMDB's own "now playing" list uses a similar window.
 */
private const val THEATRICAL_WINDOW_DAYS = 45L

/**
 * [WatchAvailability.Streaming] when there is at least one provider, otherwise
 * [WatchAvailability.Unavailable].
 */
fun List<WatchProvider>.toWatchAvailability(): WatchAvailability {
    return if (isEmpty()) WatchAvailability.Unavailable else WatchAvailability.Streaming(this)
}

/**
 * Same as [List.toWatchAvailability], except that a movie without providers whose premiere is
 * at most [THEATRICAL_WINDOW_DAYS] old is [WatchAvailability.InTheaters].
 *
 * @param today Reference date, exposed for tests.
 */
fun Movie.toWatchAvailability(
    providers: List<WatchProvider>,
    today: LocalDate = LocalDate.now()
): WatchAvailability {
    if (providers.isNotEmpty()) return WatchAvailability.Streaming(providers)

    val premiere = runCatching { LocalDate.parse(releaseDate) }.getOrNull()
        ?: return WatchAvailability.Unavailable

    return if (premiere in today.minusDays(THEATRICAL_WINDOW_DAYS)..today) {
        WatchAvailability.InTheaters
    } else {
        WatchAvailability.Unavailable
    }
}
