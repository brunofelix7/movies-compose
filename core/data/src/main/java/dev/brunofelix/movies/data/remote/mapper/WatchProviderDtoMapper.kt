package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.data.remote.dto.WatchProviderDto
import dev.brunofelix.movies.data.remote.dto.WatchProvidersRootDto
import dev.brunofelix.movies.data.util.extension.toLogoUrl
import dev.brunofelix.movies.domain.model.WatchProvider

/**
 * Maps the offers of [region] to [WatchProvider] domain models, most relevant first. A service
 * listed in more than one monetization group (e.g. subscription and ads) shows up once.
 *
 * @param region ISO 3166-1 code of the region, e.g. `BR`.
 * @return The providers, or an empty list when nothing is offered in [region].
 */
fun WatchProvidersRootDto?.toWatchProviderList(region: String): List<WatchProvider> {
    val offers = this?.results?.get(region) ?: return emptyList()
    return listOfNotNull(offers.flatrate, offers.free, offers.ads)
        .flatten()
        .filter { it.providerId != null }
        .distinctBy { it.providerId }
        .sortedBy { it.displayPriority ?: Int.MAX_VALUE }
        .map { it.toDomain() }
}

/**
 * Maps a [WatchProviderDto] (API data object) to a [WatchProvider] domain model.
 *
 * @return A [WatchProvider] domain model.
 */
fun WatchProviderDto.toDomain(): WatchProvider {
    return WatchProvider(
        id = providerId ?: -1L,
        name = providerName.orEmpty(),
        logoPath = logoPath?.toLogoUrl() ?: ""
    )
}
