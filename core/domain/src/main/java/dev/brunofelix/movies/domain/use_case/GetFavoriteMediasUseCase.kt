package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Media
import kotlinx.coroutines.flow.Flow

fun interface GetFavoriteMediasUseCase {
    operator fun invoke(): Flow<List<Media>>
}
