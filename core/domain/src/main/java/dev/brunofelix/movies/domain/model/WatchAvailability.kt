package dev.brunofelix.movies.domain.model

/**
 * Where a movie or TV show can be watched right now.
 */
sealed interface WatchAvailability {

    /** Offered by at least one streaming service, ordered by relevance. */
    data class Streaming(val providers: List<WatchProvider>) : WatchAvailability

    /** A movie that premiered recently and is not on any streaming service yet. */
    data object InTheaters : WatchAvailability

    /** Not on any streaming service, either because it is unreleased or it left the catalogs. */
    data object Unavailable : WatchAvailability
}
