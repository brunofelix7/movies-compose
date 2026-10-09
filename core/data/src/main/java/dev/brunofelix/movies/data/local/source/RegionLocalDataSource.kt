package dev.brunofelix.movies.data.local.source

/**
 * Local data source for the region the user is in, which decides the streaming catalog shown.
 */
interface RegionLocalDataSource {
    /**
     * @return The ISO 3166-1 alpha-2 code of the user's region in upper case, e.g. `BR`.
     */
    fun getRegion(): String
}
