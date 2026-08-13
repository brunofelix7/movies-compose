package dev.brunofelix.movies.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.brunofelix.movies.core.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.core.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LanguageRepository {

    private companion object {
        val LANGUAGE_KEY = stringPreferencesKey("language_preference")
    }

    override fun getLanguage(): Flow<LanguageEnum> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val code = preferences[LANGUAGE_KEY]
                LanguageEnum.fromCode(code)
            }
    }

    override suspend fun saveLanguage(language: LanguageEnum) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language.code
        }
    }
}
