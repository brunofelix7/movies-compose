package dev.brunofelix.movies.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.core.data.local.preferences.DataStorePreferenceStorage
import dev.brunofelix.movies.core.data.local.preferences.PreferenceStorage
import dev.brunofelix.movies.core.data.local.preferences.PreferencesKeys
import dev.brunofelix.movies.core.data.repository.LanguageRepositoryImpl
import dev.brunofelix.movies.core.domain.repository.LanguageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindPreferenceStorage(
        impl: DataStorePreferenceStorage
    ): PreferenceStorage

    @Binds
    @Singleton
    abstract fun bindLanguageRepository(
        impl: LanguageRepositoryImpl
    ): LanguageRepository

    companion object {

        @Provides
        @Singleton
        fun provideGson(): Gson = Gson()

        @Provides
        @Singleton
        fun providePreferencesDataStore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = { context.preferencesDataStoreFile(PreferencesKeys.DATASTORE_NAME) }
            )
        }
    }
}
