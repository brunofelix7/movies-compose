package dev.brunofelix.movies.data.test_util

import dev.brunofelix.movies.data.di.RemoteModule
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

/**
 * Builds Retrofit APIs that talk to a [MockWebServer] with the production JSON configuration.
 */
object ApiTestFactory {

    inline fun <reified T> create(server: MockWebServer): T {
        return Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(RemoteModule.provideJson().asConverterFactory("application/json".toMediaType()))
            .build()
            .create(T::class.java)
    }

    fun json(body: String): MockResponse = MockResponse.Builder()
        .code(200)
        .addHeader("Content-Type", "application/json")
        .body(body)
        .build()
}
