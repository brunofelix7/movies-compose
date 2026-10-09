package dev.brunofelix.movies.domain.repository

import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.util.Resource
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
     *
     * @return A [Resource] that fails when the language could not be stored.
     */
    suspend fun saveLanguage(language: LanguageEnum): Resource<Unit>
}
