package dev.brunofelix.movies.domain.repository

/**
 * Repository exposing information about the installed application.
 */
interface AppInfoRepository {
    /**
     * Returns the version name of the installed app, e.g. `1.0.1`.
     */
    fun getAppVersion(): String
}
