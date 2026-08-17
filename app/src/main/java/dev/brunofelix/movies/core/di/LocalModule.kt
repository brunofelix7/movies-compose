package dev.brunofelix.movies.core.di

import android.app.Application
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.core.data.local.db.AppDatabase
import dev.brunofelix.movies.core.data.local.db.dao.MediaDao
import dev.brunofelix.movies.core.data.local.source.MediaLocalDataSource
import dev.brunofelix.movies.core.data.local.source.MediaLocalDataSourceImpl
import dev.brunofelix.movies.core.domain.util.DB_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    companion object {
        @Provides
        @Singleton
        fun provideMovieDatabase(app: Application): AppDatabase {
            return Room.databaseBuilder(
                app,
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }

        @Provides
        @Singleton
        fun provideMovieDao(db: AppDatabase): MediaDao {
            return db.mediaDao
        }
    }

    @Binds
    @Singleton
    abstract fun bindMediaLocalDataSource(
        impl: MediaLocalDataSourceImpl
    ): MediaLocalDataSource
}