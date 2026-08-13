package dev.brunofelix.movies.core.data.remote.interceptor

import dev.brunofelix.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor that injects mandatory query parameters into every outgoing TMDB API request.
 *
 * This interceptor automatically adds:
 * - `api_key`: The API key from [BuildConfig.API_KEY].
 * - `language`: The default language for the response (currently set to "en").
 */
class RemoteInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("language", "en") // pt-BR
            .build()
        val newRequest = request.newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}
