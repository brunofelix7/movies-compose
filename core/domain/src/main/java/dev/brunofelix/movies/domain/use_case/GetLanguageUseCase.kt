package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import kotlinx.coroutines.flow.Flow

fun interface GetLanguageUseCase {
    operator fun invoke(): Flow<LanguageEnum>
}
