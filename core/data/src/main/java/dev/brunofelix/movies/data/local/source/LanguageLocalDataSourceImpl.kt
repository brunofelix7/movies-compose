package dev.brunofelix.movies.data.local.source

import dev.brunofelix.movies.data.local.preferences.PreferenceStorage
import dev.brunofelix.movies.data.local.preferences.PreferencesKeys
import dev.brunofelix.movies.data.util.safeLocalCall
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of [LanguageLocalDataSource] that stores the language code in [PreferenceStorage].
 */
class LanguageLocalDataSourceImpl @Inject constructor(
    private val preferences: PreferenceStorage
) : LanguageLocalDataSource {

    override fun observe(): Flow<LanguageEnum> {
        return preferences.observe(
            key = PreferencesKeys.LANGUAGE_KEY,
            defaultValue = LanguageEnum.ENGLISH.code
        ).map { code ->
            LanguageEnum.fromCode(code)
        }
    }

    override suspend fun save(language: LanguageEnum): Result<Unit> = safeLocalCall {
        preferences.put(
            key = PreferencesKeys.LANGUAGE_KEY,
            value = language.code
        )
    }
}
