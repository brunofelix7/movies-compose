package dev.brunofelix.pmovie.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.pmovie.core.data.local.MovieDatabase
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object RoomModuleTest {

    @Provides
    @Named("test_db")
    fun provideMovieDatabaseTest(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}