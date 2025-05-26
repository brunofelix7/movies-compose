package dev.brunofelix.movies.core.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.core.data.local.MovieDatabase
import dev.brunofelix.movies.core.data.local.data_source.MovieLocalDataSourceImpl
import dev.brunofelix.movies.feature.movie.domain.data_source.MovieLocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movies_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(db: MovieDatabase): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(db)
    }
}