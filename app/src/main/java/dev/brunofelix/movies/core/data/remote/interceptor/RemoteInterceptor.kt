package dev.brunofelix.movies.core.data.remote.interceptor

import dev.brunofelix.movies.BuildConfig
import dev.brunofelix.movies.core.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor that injects mandatory query parameters into every outgoing TMDB API request.
 *
 * This interceptor automatically adds:
 * - `api_key`: The API key from [BuildConfig.API_KEY].
 * - `language`: The user's preferred language from [LanguageRepository].
 */
class RemoteInterceptor @Inject constructor(
    private val languageRepository: LanguageRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val language = runBlocking {
            languageRepository.getLanguage().first()
        }
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("language", language.code)
            .build()
        val newRequest = request.newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}
