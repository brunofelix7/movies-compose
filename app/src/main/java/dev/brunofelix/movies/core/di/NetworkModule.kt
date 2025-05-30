package dev.brunofelix.movies.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.BuildConfig
import dev.brunofelix.movies.core.data.remote.MovieApi
import dev.brunofelix.movies.core.data.remote.MovieInterceptor
import dev.brunofelix.movies.core.data.remote.data_source.MovieRemoteDataSourceImpl
import dev.brunofelix.movies.core.domain.data_source.MovieRemoteDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideMovieInterceptor(): MovieInterceptor {
        return MovieInterceptor()
    }

    @Provides
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
    fun provideOkHttpClient(
        movieInterceptor: MovieInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(movieInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(15L, TimeUnit.SECONDS)
            .readTimeout(15L, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideMovieApi(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): MovieApi {
        return retrofit2.Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(api: MovieApi): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(api)
    }
}