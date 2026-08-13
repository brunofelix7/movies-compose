package dev.brunofelix.movies.core.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.BuildConfig
import dev.brunofelix.movies.core.data.remote.MovieService
import dev.brunofelix.movies.core.data.remote.TvShowService
import dev.brunofelix.movies.core.data.remote.interceptor.RemoteInterceptor
import dev.brunofelix.movies.core.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.data.remote.source.MovieRemoteDataSourceImpl
import dev.brunofelix.movies.core.data.remote.source.TvShowRemoteDataSource
import dev.brunofelix.movies.core.data.remote.source.TvShowRemoteDataSourceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    companion object {
        @Provides
        @Singleton
        fun provideMovieInterceptor(): RemoteInterceptor {
            return RemoteInterceptor()
        }

        @Provides
        @Singleton
        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                setLevel(
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                )
            }
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(
            remoteInterceptor: RemoteInterceptor,
            httpLoggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(remoteInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(15L, TimeUnit.SECONDS)
                .readTimeout(15L, TimeUnit.SECONDS)
                .build()
        }

        @Provides
        @Singleton
        fun provideGsonConverterFactory(): GsonConverterFactory {
            return GsonConverterFactory.create()
        }

        @Provides
        @Singleton
        fun provideMovieService(
            client: OkHttpClient,
            converterFactory: GsonConverterFactory
        ): MovieService {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(converterFactory)
                .build()
                .create(MovieService::class.java)
        }

        @Provides
        @Singleton
        fun provideTvShowService(
            client: OkHttpClient,
            converterFactory: GsonConverterFactory
        ): TvShowService {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(converterFactory)
                .build()
                .create(TvShowService::class.java)
        }
    }

    @Binds
    @Singleton
    abstract fun bindMovieRemoteDataSource(
        impl: MovieRemoteDataSourceImpl
    ): MovieRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindTvShowRemoteDataSource(
        impl: TvShowRemoteDataSourceImpl
    ): TvShowRemoteDataSource
}