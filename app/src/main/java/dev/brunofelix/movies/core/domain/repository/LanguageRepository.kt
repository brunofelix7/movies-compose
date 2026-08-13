package dev.brunofelix.movies.core.domain.repository

import dev.brunofelix.movies.core.domain.model.enums.LanguageEnum
import kotlinx.coroutines.flow.Flow

/**
 * Repository responsible for managing the user's preferred language for API responses.
 */
interface LanguageRepository {
    /**
     * Returns a [Flow] of the preferred [LanguageEnum].
     */
    fun getLanguage(): Flow<LanguageEnum>

    /**
     * Saves the preferred [LanguageEnum] to persistent storage.
     */
    suspend fun saveLanguage(language: LanguageEnum)
}
