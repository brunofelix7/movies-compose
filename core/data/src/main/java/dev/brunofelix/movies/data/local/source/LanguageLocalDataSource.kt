package dev.brunofelix.movies.data.local.source

import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import kotlinx.coroutines.flow.Flow

/**
 * Local data source for the language picked in Settings.
 */
interface LanguageLocalDataSource {
    fun observe(): Flow<LanguageEnum>

    suspend fun save(language: LanguageEnum): Result<Unit>
}
