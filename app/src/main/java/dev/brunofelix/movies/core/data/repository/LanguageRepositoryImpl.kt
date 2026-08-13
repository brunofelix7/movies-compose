package dev.brunofelix.movies.core.data.repository

import dev.brunofelix.movies.core.data.local.preferences.PreferenceStorage
import dev.brunofelix.movies.core.data.local.preferences.PreferencesKeys
import dev.brunofelix.movies.core.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.core.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of [LanguageRepository] that uses [PreferenceStorage] to persist
 * the user's language choice.
 *
 * @property preferenceStorage The generic storage used to save and retrieve preferences.
 */
class LanguageRepositoryImpl @Inject constructor(
    private val preferences: PreferenceStorage
) : LanguageRepository {

    override fun getLanguage(): Flow<LanguageEnum> {
        return preferences.observe(
            key = PreferencesKeys.LANGUAGE_KEY, 
            defaultValue = LanguageEnum.ENGLISH.code
        ).map { code ->
            LanguageEnum.fromCode(code)
        }
    }

    override suspend fun saveLanguage(
        language: LanguageEnum
    ) {
        preferences.put(
            key = PreferencesKeys.LANGUAGE_KEY, 
            value = language.code
        )
    }
}
