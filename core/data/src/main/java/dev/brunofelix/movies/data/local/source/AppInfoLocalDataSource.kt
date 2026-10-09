package dev.brunofelix.movies.data.local.source

/**
 * Local data source for information about the installed application.
 */
interface AppInfoLocalDataSource {
    fun getVersionName(): String
}
