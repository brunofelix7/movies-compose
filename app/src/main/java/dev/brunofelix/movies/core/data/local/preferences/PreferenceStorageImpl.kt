package dev.brunofelix.movies.core.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of [PreferenceStorage] using Jetpack DataStore and Gson for object serialization.
 */
class PreferenceStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) : PreferenceStorage {

    override suspend fun <T> put(key: String, value: T) {
        dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key)] = value
                is Int -> preferences[intPreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                is Long -> preferences[longPreferencesKey(key)] = value
                is Float -> preferences[floatPreferencesKey(key)] = value
                else -> preferences[stringPreferencesKey(key)] = gson.toJson(value)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> get(key: String, defaultValue: T): T {
        return observe(key, defaultValue).first()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> observe(key: String, defaultValue: T): Flow<T> {
        return dataStore.data.map { preferences ->
            when (defaultValue) {
                is String -> (preferences[stringPreferencesKey(key)] ?: defaultValue) as T
                is Int -> (preferences[intPreferencesKey(key)] ?: defaultValue) as T
                is Boolean -> (preferences[booleanPreferencesKey(key)] ?: defaultValue) as T
                is Long -> (preferences[longPreferencesKey(key)] ?: defaultValue) as T
                is Float -> (preferences[floatPreferencesKey(key)] ?: defaultValue) as T
                else -> {
                    val json = preferences[stringPreferencesKey(key)] ?: return@map defaultValue
                    val type = (defaultValue as? Any)?.let { it::class.java } ?: return@map defaultValue
                    try {
                        gson.fromJson(json, type) as T
                    } catch (_: Exception) {
                        defaultValue
                    }
                }
            }
        }
    }

    override suspend fun remove(key: String) {
        dataStore.edit { preferences ->
            when {
                preferences.contains(stringPreferencesKey(key)) -> preferences.remove(stringPreferencesKey(key))
                preferences.contains(intPreferencesKey(key)) -> preferences.remove(intPreferencesKey(key))
                preferences.contains(booleanPreferencesKey(key)) -> preferences.remove(booleanPreferencesKey(key))
                preferences.contains(longPreferencesKey(key)) -> preferences.remove(longPreferencesKey(key))
                preferences.contains(floatPreferencesKey(key)) -> preferences.remove(floatPreferencesKey(key))
            }
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
