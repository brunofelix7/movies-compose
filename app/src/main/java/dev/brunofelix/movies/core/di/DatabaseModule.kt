package dev.brunofelix.movies.core.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.core.data.db.MovieDatabase
import dev.brunofelix.movies.core.data.db.dao.MovieDao
import dev.brunofelix.movies.core.data.source.MovieLocalDataSource
import dev.brunofelix.movies.core.data.source.MovieLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

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
    fun provideMovieDao(db: MovieDatabase): MovieDao {
        return db.movieDao
    }

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(dao: MovieDao): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(dao)
    }
}