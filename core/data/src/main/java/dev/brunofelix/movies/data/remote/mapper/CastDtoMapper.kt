package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.data.remote.dto.CastDto
import dev.brunofelix.movies.data.remote.dto.CreditsRootDto
import dev.brunofelix.movies.data.util.extension.toProfileUrl
import dev.brunofelix.movies.domain.model.Cast

/**
 * Maps a [CreditsRootDto] (API response) to a list of [Cast] domain models, top billed first.
 *
 * @return A list of [Cast] objects, or an empty list if the credits carry no cast.
 */
fun CreditsRootDto?.toCastList(): List<Cast> {
    return this?.cast
        ?.sortedBy { it.order ?: Int.MAX_VALUE }
        ?.map { it.toDomain() }
        ?: emptyList()
}

/**
 * Maps a [CastDto] (API data object) to a [Cast] domain model.
 *
 * @return A [Cast] domain model.
 */
fun CastDto.toDomain(): Cast {
    return Cast(
        id = id ?: -1L,
        name = name.orEmpty(),
        character = character.orEmpty(),
        profilePath = profilePath?.toProfileUrl() ?: ""
    )
}
