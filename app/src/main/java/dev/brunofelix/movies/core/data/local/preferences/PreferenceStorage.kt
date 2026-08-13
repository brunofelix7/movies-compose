package dev.brunofelix.movies.core.data.local.preferences

import kotlinx.coroutines.flow.Flow

/**
 * Interface representing a generic key-value storage for application preferences.
 */
interface PreferenceStorage {
    suspend fun <T> put(key: String, value: T)
    suspend fun <T> get(key: String, defaultValue: T): T
    fun <T> observe(key: String, defaultValue: T): Flow<T>
    suspend fun remove(key: String)
    suspend fun clear()
}
