package dev.brunofelix.movies.core.domain.model.enums

/**
 * Supported languages, both for the TMDB API and for the app's own strings.
 *
 * Adding an entry means adding a `values-*` resource folder and a line in `locales_config.xml`.
 *
 * @property code Language code sent to the API, which is also the BCP-47 tag used for the app
 * locale. `pt-BR` maps to `values-pt-rBR`, `es` to `values-es`, and `en` to the default folder.
 * @property description Name of the language written in that same language, which is why it is
 * not a translatable resource: a picker should read the same whatever the current locale is.
 */
enum class LanguageEnum(val code: String, val description: String) {
    ENGLISH("en", "English"),
    PORTUGUESE("pt-BR", "Português"),
    SPANISH("es", "Español");

    companion object {
        /**
         * Returns the [LanguageEnum] corresponding to the given [code], or [ENGLISH] as fallback.
         */
        fun fromCode(code: String?): LanguageEnum {
            return entries.find { it.code == code } ?: ENGLISH
        }
    }
}